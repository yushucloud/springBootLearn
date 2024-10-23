package com.bart.api.enums;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常状态码
 *
 * @Author noobBart
 * @Date 2023/7/16/0016 12:25
 */
public enum GlobalException {
    E10001("10001", "System error", "系统错误"),
    E10002("10002", "Service unavailable", "服务暂停"),
    E10003("10003", "Remote service error", "远程服务错误"),
    E10004("10004", "IP limit", "IP限制不能请求该资源"),
    E10005("10005", "Permission denied, need a high level appkey", "该资源需要appkey拥有授权"),
    E10006("10006", "Source paramter (appkey) is missing", "缺少source (appkey) 参数"),
    E10007("10007", "only support mediatype 'Content-type:application/json'", "只支持的MediaType 'Content-type:application/json'"),
    E10008("10008", "Param error, see doc for more info", "参数错误，请参考API文档"),
    E10009("10009", "Too many pending tasks, system is busy", "任务过多，系统繁忙"),
    E10010("10010", "Job expired", "任务超时"),
    E10011("10011", "{} request error", "{} 请求错误"),
    E10012("10012", "Illegal request", "非法请求"),
    E10013("10013", "Invalid locals user", "不合法的Locals用户"),
    E10014("10014", "Insufficient app permissions", "应用的接口访问权限受限"),
    E10016("10016", "Miss required parameter , see doc for more info", "缺失必选参数，请参考API文档"),
    E10017("10017", "Parameter (%s)'s value invalid, expect ({}) , but get ({}) , see doc for more info", "参数值非法，需为 (%s)，实际为 (%s)，请参考API文档"),
    E10018("10018", "Request body length over limit", "请求长度超过限制"),
    E10020("10020", "Request api not found", "接口不存在"),
    E10021("10021", "HTTP method is not suported for this request", "请求的HTTP METHOD不支持，请检查是否选择了正确的POST/GET/PUT/DELETE方式"),
    E10022("10022", "IP requests out of rate limit", "IP请求频次超过上限"),
    E10023("10023", "User requests out of rate limit", "用户请求频次超过上限"),
    E10024("10024", "User requests for ({}) out of rate limit", "用户请求特殊接口 ({}) 频次超过上限"),
    E10025("10025", "A database error occurred. Please try again", "数据库操作出错，请重试"),
    E10026("10026", "No permission to access data", "无权限访问该数据"),
    E11001("11001", "{} not null", "{} 不能为空"),
    E20101("20101", "", "「{}」手机号码不正确"),
    E20102("20102", "", "手机号码不能为空"),
    E20103("20103", "", "「{}」用户名已存在"),
    E20104("20104", "", "验证码不能为空"),
    E20105("20105", "", "验证码不正确"),
    E20106("20106", "", "「用户名或手机号」不存在"),
    E20107("20107", "", "「{}」没有权限访问"),
    E20108("20108", "", "账号或密码不正确"),
    E20109("20109", "", "token刷新失败"),
    E20110("20110", "", "累计验证失败{}次,请在{}秒后重试"),
    E20111("20111", "", "累计验证失败{}次,请在{}分钟后重试"),
    E20112("20112", "", "请勿重复发送"),
    E20113("20113", "token invalid", "Token 无效"),
    E20114("20114", "", "[{}]邮箱不正确"),
    E20115("20115", "", "验证码已过期"),
    E20116("20116", "", "没有登录"),
    E20117("20117", "", "token 不合法"),
    E20118("20118", "", "token 已过期"),
    E20119("20119", "", "账号已在别处登录,请重新登录"),
    E20120("20120", "", "已超过{}天未登录,请重新登录"),
    E20201("20201", "", "附件记录到数据库失败"),
    E20202("20202", "", "暂不支持bytes[]"),
    E20301("20301", "", "发送短信失败:{}"),
    E20302("20302", "", "发送短信异常:{}"),
    E20401("20401", "Pay error", "支付失败"),
    E20402("20402", "Refund error", "退款失败"),
    E20403("20403", "Order query fail", "查询订单失败"),
    E20404("20404", "Notify request params is null", "回调接收参数为空"),
    E20405("20405", "Sign fail", "签名失败"),
    E20406("20406", "Verify fail", "验证失败"),
    E20407("20407", "Order was already Handled", "订单已经处理过，无需重复处理"),
    E20408("20408", "The amount returned is inconsistent with the order amount of the merchant.", "返回的订单金额与商户的订单金额不一致"),
    E20409("20409", "Refund query fail", "退款查询失败"),
    E20410("20410", "Refund query fail", "商户订单号不存在"),
    E20411("20411", "", "订单已处理"),
    E20412("20412", "", "商户账户不一致，疑存在欺诈行为，立即报警！"),
    E20413("20413", "", "金额和类型与订单平台不一致，疑存在欺诈行为，立即报警！"),
    E20414("20414", "FAIL", "回调失败"),
    E20415("20415", "", "未找到该订单来源: {}"),
    E20416("20501", "Already Booked", "选择的日期范围存在已租状态，请重新选择！"),
    E20601("20601", "", "可用余额不足！"),
    E20602("20602", "", "订单[{}]不能重复扣款！"),
    E20603("20603", "", "订单[{}]没有支付记录，不能退款！"),
    E20604("20604", "", "订单[{}]不能重复返现！"),
    E20605("20605", "", "订单[{}]不能重复退款！"),
    E20701("20701", "", "没有找到占用记录,请联系技术人员"),
    E20711("20711", "", "[{}]已经没有足够房源"),
    E20712("20712", "", "房东不接新订单了，如有问题请与客服联系。"),
    E21101("21101", "", "超过该优惠券今天的领取次数:{}"),
    E21102("21102", "", "超过该优惠券的领取次数:{}"),
    E22101("22101", "", "暂不支持多房型支付");

    private String code;
    private String english;
    private String chinese;
    private String[] parameters;
    private static Map<String, GlobalException> code2Enum = new HashMap();

    private GlobalException(String code, String english, String chinese) {
        this.code = code;
        this.english = english;
        this.chinese = chinese;
    }

    public String getEnglish() {
        return StrUtil.isBlank(this.english) ? this.chinese : this.english;
    }

    public String getChinese() {
        return StrUtil.isBlank(this.chinese) ? this.english : this.chinese;
    }

    public String getErrorMsg() {
        String msg = StrUtil.isBlank(this.english) ? this.chinese : this.english;
        return this.parameters != null ? StrUtil.format(msg, this.parameters) : msg;
    }

    public String getErrorMsg(String... args) {
        String msg = this.english;
        if (StrUtil.isBlank(this.english)) {
            msg = this.chinese;
        }

        return StrUtil.format(msg, args);
    }

    public GlobalException setParameter(String... args) {
        this.parameters = args;
        return this;
    }

    public static GlobalException fromCode(String code) {
        return (GlobalException) code2Enum.get(code);
    }

    public String getCode() {
        return this.code;
    }

    public String[] getParameters() {
        return this.parameters;
    }

    static {
        GlobalException[] var0 = values();
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2) {
            GlobalException value = var0[var2];
            code2Enum.put(value.getCode(), value);
        }

    }
}
