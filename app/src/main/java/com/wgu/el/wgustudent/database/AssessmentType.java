package com.wgu.el.wgustudent.database;

import android.arch.persistence.room.TypeConverter;

public enum AssessmentType {
        PERFORMANCE(0), OBJECTIVE(1);
        private final int type;

        @TypeConverter
        public static AssessmentType fromType(Integer type) {
            for (AssessmentType t : values()) {
                if (t.type == type) {
                    return (t);
                }
            }
            return (null);
        }

        @TypeConverter
        public static Integer fromType(AssessmentType t) {
            return (t.type);
        }

        AssessmentType(int type) {
            this.type = type;
        }

}
