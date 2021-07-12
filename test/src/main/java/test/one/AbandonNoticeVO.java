package test.one;

import java.util.Date;



public class AbandonNoticeVO implements java.io.Serializable {

    private static final long serialVersionUID = -1300742296285581640L;
    /**
     * 数量
     */
    private String num;

    /**
     * 编号
     */
    private String cerid;

    /**
     * 不动产权证号、不动产登记证明号
     */
    private String bdcqzh;

    /**
     * 权利人
     */
    private String qlr;

    /**
     * 权利类型
     */
    private String qllx;

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 不动产坐落
     */
    private String fdzl;

    /**
     * 备注
     */
    private String bz;

    /**
     * 公告单位
     */

    private String ggdy;

    /**
     * 公告作废时间
     */

    private Date zxsj;

    /**
     * 数据生成时间，即插入到中间库时间（sysdate）
     */

    private Date sjscsj;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCerid() {
        return cerid;
    }

    public void setCerid(String cerid) {
        this.cerid = cerid;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getFdzl() {
        return fdzl;
    }

    public void setFdzl(String fdzl) {
        this.fdzl = fdzl;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getGgdy() {
        return ggdy;
    }

    public void setGgdy(String ggdy) {
        this.ggdy = ggdy;
    }

    public Date getZxsj() {
        return zxsj;
    }

    public void setZxsj(Date zxsj) {
        this.zxsj = zxsj;
    }

    public Date getSjscsj() {
        return sjscsj;
    }

    public void setSjscsj(Date sjscsj) {
        this.sjscsj = sjscsj;
    }
}
