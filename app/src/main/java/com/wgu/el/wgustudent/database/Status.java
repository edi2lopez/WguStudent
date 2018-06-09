package com.wgu.el.wgustudent.database;

import android.arch.persistence.room.TypeConverter;

public enum Status {
    IN_PROGRESS(0), COMPLETED(1), FAILED(2), NEEDS_APPROVAL(3);
    private final int level;

    @TypeConverter
    public static Status fromLevel(Integer level) {
        for (Status s : values()) {
            if (s.level == level) {
                return (s);
            }
        }
        return (null);
    }

    @TypeConverter
    public static Integer fromStatus(Status s) {
        return (s.level);
    }

    Status(int level) {
        this.level = level;
    }

//    IN_PROGRESS("In progress"),
//    COMPLETED("Completed"),
//    DROPPED("Dropped"),
//    NEEDS_APPROVAL("Needs Approval");
//
//    private final String level;
//
//    @TypeConverter
//    public static Status fromLevel(String level) {
//        for (Status p : values()) {
//            if (p.level == level) {
//                return (p);
//            }
//        }
//        return (null);
//    }
//
//    @TypeConverter
//    public static String fromStatus(Status p) {
//        return (p.level);
//    }
//
//    Status(String level) {
//        this.level = level;
//    }
//
//    @Override
//    public String toString() {
//        return level;
//    }

}