package com.enjoyu.admin.controller.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface PageReq {
    Integer getPageIndex();

    Integer getPageSize();

    default <T> Page<T> getPage() {
        long i = getPageIndex() == null ? 0L : getPageIndex();
        long s = getPageSize() == null ? 10L : getPageSize();
        return new Page<>(i, s);
    }
}
