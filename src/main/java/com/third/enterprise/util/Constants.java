package com.third.enterprise.util;


public final class Constants {

    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "操作成功";

    public static final String PATH_NOT_FOUND_ERROR_CODE = "404";
    public static final String PATH_NOT_FOUND_ERROR_MESSAGE = "请求的路径不存在：%s";

    public static final String CONTENT_TYPE_NOT_SUPPORT_ERROR_CODE = "405";
    public static final String CONTENT_TYPE_NOT_SUPPORT_ERROR_MESSAGE = "Content type '%s' 不支持";


    public static final String METHOD_NOT_SUPPORT_ERROR_CODE = "405";
    public static final String METHOD_NOT_SUPPORT_ERROR_MESSAGE = "请求方式不支持：s%";

    public static final String CALL_SERVICE_ERROR_CODE = "500";
    public static final String CALL_SERVICE_ERROR_MESSAGE = "服务异常";

    public static final String PARAMETER_NOT_VALID_ERROR_CODE = "10001";
    public static final String PARAMETER_NOT_VALID_ERROR_MESSAGE = "请求参数不合法:'%s'";

    public static final String BODY_NOT_VALID_ERROR_CODE = "10002";
    public static final String BODY_NOT_VALID_ERROR_MESSAGE = "请求体不存在或格式错误";

    public static final String ACCOUNT_EXISTS_ERROR_CODE = "10003";
    public static final String ACCOUNT_EXISTS_ERROR_MESSAGE = "注册账号已经存在";

    public static final String EMPTY_DATA_ERROR_CODE = "10004";
    public static final String EMPTY_DATA_ERROR_MESSAGE = "未查询到数据";

    public static final String ROLE_NAME_EXISTS_ERROR_CODE = "10005";
    public static final String ROLE_NAME_EXISTS_ERROR_MESSAGE = "角色名已经存在";

    public static final String DATA_HANDLE_ERROR_CODE = "10006";
    public static final String DATA_HANDLE_ERROR_MESSAGE = "数据处理失败";

    public static final String ROLE_BIND_ERROR_CODE = "10007";
    public static final String ROLE_BIND_ERROR_MESSAGE = "角色已绑定，无法删除";

    public static final String PRODUCT_STATE_ERROR_CODE = "10008";
    public static final String PRODUCT_STATE_ERROR_MESSAGE = "项目状态不正确，无法验收";

    public static final String FILE_HANDLE_ERROR_CODE = "10009";
    public static final String FILE_HANDLE_ERROR_MESSAGE = "文件处理失败";

    public static final String TOKEN_ERROR_CODE = "11001";
    public static final String TOKEN_ERROR_MESSAGE = "token丢失或错误";

    public static final String CREDENTIALS_ERROR_CODE = "11002";
    public static final String CREDENTIALS_ERROR_MESSAGE = "账号或密码错误";

    public static final String ACCESS_DENIED_ERROR_CODE = "11003";
    public static final String ACCESS_DENIED_ERROR_MESSAGE = "权限拒绝";

    public static final String REGISTER_ERROR_CODE = "11004";
    public static final String REGISTER_ERROR_MESSAGE = "注册失败";


    public class RedisKey{

        public static final String OPERATION_REGISTER = "operation_register:";

    }

    public class ProductState{

        public static final String WAIT_ORDER = "1";

        public static final String WAIT_CONFIRM = "2";

        public static final String WAIT_CHECK = "3";

        public static final String ALREADLY_CHECKED = "4";
    }
}
