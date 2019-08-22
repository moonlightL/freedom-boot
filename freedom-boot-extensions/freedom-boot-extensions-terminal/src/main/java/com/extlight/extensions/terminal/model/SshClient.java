package com.extlight.extensions.terminal.model;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author MoonlightL
 * @Title: SshClient
 * @ProjectName: freedom-boot
 * @Description: ssh 连接客户端
 * @DateTime: 2019/8/21 16:47
 */
@Setter
@Getter
@ToString
public class SshClient implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * SSH 配置
	 */
	private SshConfig sshConfig;

	/**
	 * WebSocket 客户端
	 */
	private WebSocketSession webSocketSession;

	/**
	 * ssh 连接
	 */
	private Connection connection;

	/**
	 *  SSH 连接会话
	 */
	private Session session;

	/**
	 * 输出流
	 */
	private BufferedWriter writer;

	/**
	 * 输出任务
	 */
	private OutputTask outputTask;

	private static ExecutorService executorService = Executors.newSingleThreadExecutor();

	public SshClient(SshConfig sshConfig, WebSocketSession webSocketSession) {
		this.sshConfig = sshConfig;
		this.webSocketSession = webSocketSession;
	}

	/**
	 * 连接服务器
	 * @return
	 */
	public boolean connect() {
		try {
			this.connection = new Connection(this.sshConfig.getHostname(), 22);
			// 连接
			this.connection.connect();

			// 认证
			boolean isSuccess = this.connection.authenticateWithPassword(this.sshConfig.getUsername(), this.sshConfig.getPassword());
			if (isSuccess) {
				this.session = this.connection.openSession();
				this.session.requestPTY("xterm", 240, 30, 0, 0, null);
				// 启动 shell
				this.session.startShell();

				// 设置输出流
				this.writer = new BufferedWriter(new OutputStreamWriter(this.session.getStdin(), "UTF-8"));

				// 启动输出任务
				this.outputTask = new OutputTask(this.session.getStdout(), this.webSocketSession);
				executorService.submit(this.outputTask);

				return true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 发送命令
	 * @param command
	 * @return
	 */
	public boolean sendCommand(String command) {
		try {
			this.writer.write(command);
			this.writer.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 断开连接
	 */
	public void disconnect() {
		try {

			if (this.outputTask != null) {
				this.outputTask.stop();
			}

			if (this.session != null) {
				this.session.close();
			}

			if (this.connection != null) {
				this.connection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class OutputTask implements Runnable {

		private static final String ENCODING = "UTF-8";

		private volatile boolean isStop;

		private InputStream in;

		private WebSocketSession webSocketSession;

		public OutputTask(InputStream in, WebSocketSession webSocketSession) {
			this.in = in;
			this.webSocketSession = webSocketSession;
		}

		@Override
		public void run() {
			try {

				while (!this.isStop && this.webSocketSession != null && this.webSocketSession.isOpen()) {
					this.writeToWeb(this.in);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 输出到 web
		 * @param in
		 */
		private void writeToWeb(InputStream in) {
			try {
				byte [] buff = new byte[8192];

				int len;
				StringBuffer sb = new StringBuffer();
				while((len = in.read(buff)) > 0) {
					//设定从0 开始
					sb.setLength(0);

					//读取数组里面的数据，进行补码
					for (int i = 0; i < len; i++){
						//进行补码操作
						char c = (char) (buff[i] & 0xff);
						sb.append(c);
					}

					//写数据到服务器端
					System.out.println(new String(sb.toString().getBytes("ISO-8859-1"), ENCODING));
					this.webSocketSession.sendMessage(new TextMessage(new String(sb.toString().getBytes("ISO-8859-1"), ENCODING)));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 停止线程
		 */
		public void stop() {
			this.isStop = !this.isStop;
		}
	}

}
