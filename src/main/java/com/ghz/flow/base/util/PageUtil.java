package com.ghz.flow.base.util;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页工具
 *
 * @author henryyan
 */
public class PageUtil {

    public static int PAGE_SIZE = 10;

    /**
     * 给出两个参数pageNumber 为第几页
     *          pageSize  每页显示多少条记录
     * @param page
     * @param request
     * @return
     */
    public static int[] init(Page<?> page, HttpServletRequest request) {
        //得到第几页
        int pageNumber = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("p"), "1"));
        page.setPageNo(pageNumber);
        //每页显示多少条数据
        int pageSize = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("ps"), String.valueOf(PAGE_SIZE)));
        page.setPageSize(pageSize);
        //当前显示第一条记录 为第一条
        int firstResult = page.getFirst() - 1;
        int maxResults = page.getPageSize();
        return new int[]{firstResult, maxResults};
    }

}
