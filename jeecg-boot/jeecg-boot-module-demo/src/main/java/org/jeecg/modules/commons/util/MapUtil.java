/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 *
 * http://www.weixin4j.org/sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jeecg.modules.commons.util;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;


public class MapUtil {

    /**
     * Map key 升序排序
     * <p>
     * 排序方式：ASCII码从小到大排序（字典序）
     *
     * @param map 需排序的map集合
     * @return 排序后的map集合
     */
    public static Map<String, String> sortAsc(Map<String, String> map) {
        HashMap<String, String> tempMap = new LinkedHashMap<String, String>();
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        //排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().toString().compareTo(o2.getKey());
            }
        });

        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> item = infoIds.get(i);
            tempMap.put(item.getKey(), item.getValue());
        }
        return tempMap;
    }


    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, String> buildMap(Object obj) {
        Map<String, String> map = new HashMap<>();

        try {
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();

                //如果 element 注解 name 字段设置了内容, 使用其当成字段名
                org.simpleframework.xml.Element element = field.getAnnotation(org.simpleframework.xml.Element.class);
                if (element != null && StringUtils.isNotEmpty(element.name())) {
                    fieldName = element.name();
                }

                String value = field.get(obj) == null ? "" : String.valueOf(field.get(obj));
                map.put(fieldName, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * Map key 升序排序
     * <p>
     * 排序方式：ASCII码从小到大排序（字典序）字符串小写
     *
     * @param map 需排序的map集合
     * @return 排序后的map集合
     */
    public static Map<String, String> sortAscLowerCase(Map<String, String> map) {
        HashMap<String, String> tempMap = new LinkedHashMap<String, String>();
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        //排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
//                System.out.println(o1.getKey() +"---"+o2.getKey());
//                System.out.println(o1.getKey().toLowerCase().compareTo(o2.getKey().toLowerCase()));
//                System.out.println(o1.getKey().toString().compareTo(o2.getKey()));
//                return o1.getKey().toString().compareTo(o2.getKey());
                return o1.getKey().toLowerCase().compareTo(o2.getKey().toLowerCase());
            }
        });

        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> item = infoIds.get(i);
            tempMap.put(item.getKey(), item.getValue());
        }
        return tempMap;
    }
    /**
     * url 参数串连
     *
     * @param map            需拼接的参数map
     * @param valueUrlEncode 是否需要对map的value进行url编码
     * @return 拼接后的URL键值对字符串
     */
    public static String mapJoin(Map<String, String> map, boolean valueUrlEncode) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            if (map.get(key) != null && !"".equals(map.get(key))) {
                try {
                    String temp = (key.endsWith("_") && key.length() > 1) ? key.substring(0, key.length() - 1) : key;
                    sb.append(temp);
                    sb.append("=");
                    //获取到map的值
                    String value = map.get(key);
                    //判断是否需要url编码
                    if (valueUrlEncode) {
                        value = URLEncoder.encode(map.get(key), "utf-8").replace("+", "%20");
                    }
                    sb.append(value);
                    sb.append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 简单 xml 转换为 Map
     *
     * @param xml
     * @return Map对象
     */
    public static Map<String, String> xmlToMap(String xml) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getChildNodes();
            Map<String, String> map = new LinkedHashMap<String, String>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                map.put(e.getNodeName(), e.getTextContent());
            }
            return map;
        } catch (DOMException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将map转成xml
     */
//    public static String mapToXml(Map<String, String> map) throws Exception {
//        Map<String, Map<String, String>> xmlMap = new HashMap<String, Map<String, String>>();
//        xmlMap.put("xml", map);
//        return XML.toXML(xmlMap).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").trim();
//    }

    public static Map<String, String> toStringMap(Map<String, Object> sourceMap) {
        Map<String, String> targetMap = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : sourceMap.entrySet()){
            targetMap.put(entry.getKey(), entry.getValue() + "");
        }
        return targetMap;
    }

    public static Map<String, Object> toObjectMap(Map<String, String> sourceMap) {
        Map<String, Object> targetMap = new HashMap<String, Object>();
        for (Map.Entry<String, String> entry : sourceMap.entrySet()) {
            targetMap.put(entry.getKey(), entry.getValue() + "");
        }
        return targetMap;
    }
}
