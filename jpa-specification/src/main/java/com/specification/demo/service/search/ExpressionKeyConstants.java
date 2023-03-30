package com.specification.demo.service.search;

import java.util.Arrays;
import java.util.List;

public class ExpressionKeyConstants {

    public static final String ID = "id";
    public static final String CREATION_DATE = "creationDate";




    public static List<String> get_YourCategoryName_AdminKeys() {
        return Arrays.asList(ID, CREATION_DATE);
    }

    public static List<String> get_YourCategoryName_PublicKeys() {
        return Arrays.asList(ID, CREATION_DATE);
    }
}
