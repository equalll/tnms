package com.tigercel.tnms.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PageBean implements Serializable {

    @NotNull
    private int page;

    @NotNull
    private int size;

    private String sortName = "updatedAt";

    private String sortOrder = "asc";
}
