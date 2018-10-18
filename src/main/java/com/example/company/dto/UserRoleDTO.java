package com.example.company.dto;

import java.util.Date;

public class UserRoleDTO {


    private String usercode;

    private String rolecode;

    private String opinstcode;


    private String oprcode;

    private Date opdate;


    private static final long serialVersionUID = 1L;

 
    public String getUsercode() {
        return usercode;
    }


    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode == null ? null : rolecode.trim();
    }

	
    public String getOpinstcode() {
        return opinstcode;
    }

    public void setOpinstcode(String opinstcode) {
        this.opinstcode = opinstcode == null ? null : opinstcode.trim();
    }


    public String getOprcode() {
        return oprcode;
    }

    public void setOprcode(String oprcode) {
        this.oprcode = oprcode == null ? null : oprcode.trim();
    }


    public Date getOpdate() {
        return opdate;
    }


    public void setOpdate(Date opdate) {
        this.opdate = opdate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", usercode=").append(usercode);
        sb.append(", rolecode=").append(rolecode);
        sb.append(", opinstcode=").append(opinstcode);
        sb.append(", oprcode=").append(oprcode);
        sb.append(", opdate=").append(opdate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}
