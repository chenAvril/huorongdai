package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class CityBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * children : [{"text":"塞浦路斯","value":"357"},{"text":"马来西亚","value":"60"},{"text":"香港","value":"852"},{"text":"日本","value":"81"},{"text":"新加坡","value":"65"},{"text":"中国","value":"86"}]
         * text : 境外
         * value : 0
         */

        private String text;
        private String value;
        private List<ChildrenBean> children;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * text : 塞浦路斯
             * value : 357
             */

            private String text;
            private String value;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
