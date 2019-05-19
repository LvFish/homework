package com.test.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放全局变量
 * Created by sl on 16-3-3.
 */
public class GlobalVariable {
    Map<String , String> map;

    private void init() {
        if(this.map == null) {
            this.map = new HashMap<String , String>();
        }

        this.map.put("assetsPath", "/homework/assets");
        this.map.put("commonPath", "/homework/common");
        this.map.put("rootPath", "/homework");
        this.map.put("requestPath","http://localhost:8080/homework");
    }

    public Map<String , String> getAll() {
        this.init();
        return this.map;
    }
}
