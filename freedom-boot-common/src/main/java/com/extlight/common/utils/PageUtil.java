package com.extlight.common.utils;

import com.extlight.common.base.BaseResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author MoonlightL
 * @Title: PageUtil
 * @ProjectName: freedom-boot
 * @Description: 分页工具
 * @DateTime: 2019/8/20 16:01
 */
public class PageUtil {

	private PageUtil() {}

	/**
	 * 转成 VO 模型
	 * @param pageInfo
	 * @param <V>
	 * @return
	 */
	public static <V> PageInfo<V> toVO(PageInfo<?> pageInfo) {
		List<?> list = pageInfo.getList();
		List<V> data = new ArrayList<>(list.size());
		list.stream().forEach(i -> {
			if (i instanceof BaseResponse) {
				Class voClass = ((BaseResponse) i).getVoClass();
				try {
					V o = (V) voClass.newInstance();
					BeanUtils.copyProperties(i, o);
					data.add(o);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Page<V> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), false);
		page.addAll(data);
		page.setTotal(pageInfo.getTotal());

		return new PageInfo<>(page);
	}
}
