package com.dtd.letsgodubki;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Egor on 14/09/15.
 */
public class ArrayDorm {

        private boolean Error;

        @SerializedName("dorm7")
        private String Dorm7;
        @SerializedName("dorm91")
        private String Dorm91;
        @SerializedName("dorm92")
        private String Dorm92;

        private ArrayDorm(String dorm7, String dorm91, String dorm92) {
            Dorm7 = dorm7;
            Dorm91 = dorm91;
            Dorm92 = dorm92;
        }

        public ArrayDorm() {
        }

        public String getDorm7() {
            return Dorm7;
        }

        public void setDorm7(String dorm7) {
            Dorm7 = dorm7;
        }

        public String getDorm91() {
            return Dorm91;
        }

        public void setDorm91(String dorm91) {
            Dorm91 = dorm91;
        }

        public String getDorm92() {
            return Dorm92;
        }

        public void setDorm92(String dorm92) {
            Dorm92 = dorm92;
        }

        public boolean hasError() {
            return Error;
        }

        public void setError(boolean error) {
            Error = error;
        }
}
