package com.extlight.extensions.task.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.log.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.extensions.task.component.TaskModule;
import com.extlight.extensions.task.constant.TaskJobExceptionEnum;
import com.extlight.extensions.task.model.TaskJob;
import com.extlight.extensions.task.model.dto.TaskJobDTO;
import com.extlight.extensions.task.service.TaskJobService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @Author MoonlightL
 * @ClassName: TaskJobController
 * @ProjectName: freedom-boot
 * @Description: 任务作业控制器
 * @DateTime: 2019-08-14 17:44:56
 */
@Controller
@RequestMapping("/task/job")
public class TaskJobController extends BaseController {

	@Autowired
	private TaskJobService taskJobService;

	/**
	 * 新增页面
	 *
	 * @param resultMap
	 * @return
	 * @throws GlobalException
	 */
	@GetMapping("/saveUI.html")
	@RequiresPermissions("task:job:save")
	public String saveUI(Map<String, Object> resultMap) throws GlobalException {
		return render(SAVE_PAGE, resultMap);
	}

	/**
	 * 修改页面
	 *
	 * @param id
	 * @param resultMap
	 * @return
	 * @throws GlobalException
	 */
	@GetMapping("/updateUI/{id}.html")
	@RequiresPermissions("task:job:update")
	public String updateUI(@PathVariable("id") Long id, Map<String, Object> resultMap) throws GlobalException {
		TaskJob target = this.taskJobService.getById(id);
		if (target == null) {
			ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
		}

		resultMap.put("vo", target);
		resultMap.put("readOnly", false);
		return render(UPDATE_PAGE, resultMap);
	}

	/**
	 * 列表页面
	 *
	 * @param resultMap
	 * @return
	 * @throws GlobalException
	 */
	@GetMapping("/listUI.html")
	@RequiresPermissions("task:job:listUI")
	public String listUI(Map<String, Object> resultMap) throws GlobalException {
		return render(LIST_PAGE, resultMap);
	}

	/**
	 * 详情页面
	 *
	 * @param id
	 * @param resultMap
	 * @return
	 * @throws GlobalException
	 */
	@GetMapping("/detailUI/{id}.html")
	@RequiresPermissions("task:job:query")
	public String detailUI(@PathVariable("id") Long id, Map<String, Object> resultMap) throws GlobalException {
		TaskJob target = this.taskJobService.getById(id);
		if (target == null) {
			ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
		}

		resultMap.put("vo", target);
		resultMap.put("readOnly", true);
		return render(DETAIL_PAGE, resultMap);
	}

	//
	@PostMapping("/save.json")
	@RequiresPermissions("task:job:save")
	@ResponseBody
	@ActionLog(value = "新增", module = TaskModule.class, actionType = ActionEnum.SAVE)
	public Result save(@Validated(BaseRequest.Save.class) TaskJobDTO taskJobDTO) throws GlobalException {
		TaskJob taskJob = taskJobDTO.toDoModel();
		return this.taskJobService.save(taskJob) > 0 ? Result.success() : Result.fail();
	}

	@PostMapping("/remove.json")
	@RequiresPermissions("task:job:remove")
	@ResponseBody
	@ActionLog(value = "删除", module = TaskModule.class, actionType = ActionEnum.REMOVE)
	public Result remove(@RequestParam String idStr) throws GlobalException {
		String[] idArr = idStr.split(",");
		int num;
		if (idArr.length == 1) {
			num = this.taskJobService.remove(Long.valueOf(idArr[0]));
		} else {
			String[] idStrArr = idStr.split(",");
			List<Long> idList = new ArrayList<>(idStr.length());
			Arrays.stream(idStrArr).forEach(i -> idList.add(Long.valueOf(i)));
			num = this.taskJobService.removeBatch(idList);
		}
		return num > 0 ? Result.success() : Result.fail();
	}

	@PostMapping("/update.json")
	@RequiresPermissions("task:job:update")
	@ResponseBody
	@ActionLog(value = "编辑", module = TaskModule.class, actionType = ActionEnum.UPDATE)
	public Result update(@Validated(BaseRequest.Update.class) TaskJobDTO taskJobDTO) throws GlobalException {
		TaskJob target = this.taskJobService.getById(taskJobDTO.getId());
		if (target == null) {
			ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
		}

		TaskJob taskJob = taskJobDTO.toDoModel();
		return this.taskJobService.update(taskJob) > 0 ? Result.success() : Result.fail();
	}

	@GetMapping("/list.json")
	@RequiresPermissions("task:job:listUI")
	@ResponseBody
	public Result list(@Validated(BaseRequest.Query.class) TaskJobDTO params) throws GlobalException {
		PageInfo<TaskJob> pageInfo = this.taskJobService.page(params);
		return Result.success(pageInfo);
	}

	@PostMapping("/starJob.json")
	@RequiresPermissions("task:job:start")
	@ResponseBody
	@ActionLog(value = "启动定时器", module = TaskModule.class, actionType = ActionEnum.UPDATE)
	public Result starJob(Long taskJobId) throws GlobalException {
		return this.taskJobService.starJob(taskJobId) > 0 ? Result.success() : Result.fail();
	}

	@PostMapping("/pauseJob.json")
	@RequiresPermissions("task:job:pause")
	@ResponseBody
	@ActionLog(value = "暂停定时器", module = TaskModule.class, actionType = ActionEnum.UPDATE)
	public Result pauseJob(Long taskJobId) throws GlobalException {
		return this.taskJobService.pauseJob(taskJobId) > 0 ? Result.success() : Result.fail();
	}

}
