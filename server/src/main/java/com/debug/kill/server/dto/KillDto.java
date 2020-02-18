package com.debug.kill.server.dto;/*
 *FileName:  KillDto
 * Author:   lvchengming
 * Description: TODO
 * Date  :   2020/2/17 21:28
 * Version: 1.0
 * */

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
@ToString
public class KillDto implements Serializable {

    @NotNull
    private Integer killId;

    private Integer userId;
}
