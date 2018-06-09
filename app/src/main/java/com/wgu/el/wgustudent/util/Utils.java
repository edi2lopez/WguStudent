package com.wgu.el.wgustudent.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    //get String date formatted by pattern MM/dd/yyyy
    public static String getDateFormatted(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return simpleDateFormat.format(date);
    }

}
