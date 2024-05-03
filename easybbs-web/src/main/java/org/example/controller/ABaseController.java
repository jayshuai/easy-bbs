package org.example.controller;

import cn.hutool.core.bean.BeanUtil;
import org.example.entity.constants.Constants;
import org.example.entity.dto.SessionShareDto;
import org.example.entity.dto.SessionWebUserDto;
import org.example.entity.enums.ResponseCodeEnum;
import org.example.entity.vo.PaginationResultVO;
import org.example.entity.vo.ResponseVO;
import org.example.utils.WebToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;


public class ABaseController extends WebToolUtils {

    private static final Logger logger = LoggerFactory.getLogger(ABaseController.class);

    protected static final String STATUC_SUCCESS = "success";

    protected static final String STATUC_ERROR = "error";

    protected <T> ResponseVO getSuccessResponseVO(T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATUC_SUCCESS);
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }

    protected <S, T> PaginationResultVO<T> convert2PaginationVO(PaginationResultVO<S> result, Class<T> classz) {
        PaginationResultVO<T> resultVO = new PaginationResultVO<>();
        resultVO.setList(BeanUtil.copyToList(result.getList(), classz));
        resultVO.setPageNo(result.getPageNo());
        resultVO.setPageSize(result.getPageSize());
        resultVO.setPageTotal(result.getPageTotal());
        resultVO.setTotalCount(result.getTotalCount());
        return resultVO;
    }

    protected SessionWebUserDto getUserInfoFromSession(HttpSession session) {
        SessionWebUserDto sessionWebUserDto = (SessionWebUserDto) session.getAttribute(Constants.SESSION_KEY);
        return sessionWebUserDto;
    }


    protected SessionShareDto getSessionShareFromSession(HttpSession session, String shareId) {
        SessionShareDto sessionShareDto = (SessionShareDto) session.getAttribute(Constants.SESSION_SHARA_KEY + shareId);
        return sessionShareDto;
    }
}
