package cn.com.huateng.account.model;



import java.io.Serializable;

/**
 * User: 董培基
 * Date: 13-9-6
 * Time: 上午9:05
 */
public class Register implements Serializable {

    /*统一编号*/
    private String unifyId;

    /*证件类型*/
    private String idType;

    /*证件号码*/
    private String idNo;

    /*姓名*/
    private String name;

    /*性别*/
    private String gender;

    /*密保问题*/
    private String question;

    /*密保问题答案*/
    private String answer;

    /*用户 密码*/
    private String passWd;

    /*区号*/
    private String areaCode;

    /*城市区号*/
    private String cityCode;

    /*国籍*/
    private String nationality;

    /*职业*/
    private String profession;

    /*证件有效期*/
    private String certExpiryDate;

    /*地址*/
    private String address;

    public Register() {

    }

    public Register(String unifyId, String idType, String idNo,
                    String name, String gender, String question,
                    String answer, String passWd, String areaCode,
                    String cityCode) {
        this.unifyId = unifyId;
        this.idType = idType;
        this.idNo = idNo;
        this.name = name;
        this.gender = gender;
        this.question = question;
        this.answer = answer;
        this.passWd = passWd;
        this.areaCode = areaCode;
        this.cityCode = cityCode;
    }

    public String getUnifyId() {
        return unifyId;
    }

    public void setUnifyId(String unifyId) {
        this.unifyId = unifyId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCertExpiryDate() {
        return certExpiryDate;
    }

    public void setCertExpiryDate(String certExpiryDate) {
        this.certExpiryDate = certExpiryDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
