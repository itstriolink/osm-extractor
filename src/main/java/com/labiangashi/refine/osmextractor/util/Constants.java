/*
 * MIT License
 *
 * Copyright (c) 2021 Labian Gashi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.labiangashi.refine.osmextractor.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Constants {
    public static final List<String> OVERPASS_INSTANCES = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("https://lz4.overpass-api.de/api/interpreter");
                add("https://z.overpass-api.de/api/interpreter");
                add("https://overpass.openstreetmap.ru/api/interpreter");
                add("https://overpass.openstreetmap.fr/api/interpreter");
                add("https://overpass.osm.ch/api/interpreter");
                add("https://overpass.kumi.systems/api/interpreter");
                add("https://overpass.nchc.org.tw/api/interpreter");
            }});

    public class Importing {
        public static final String LATITUDE_COLUMN_NAME = "latitude";
        public static final String LONGITUDE_COLUMN_NAME = "longitude";
        public static final String POINT_DELIMITED_COLUMN_NAME = "point_delimited";
        public static final String WKT_COLUMN_NAME = "WKT";
        public static final String GENERATED_COLUMN_DESCRIPTION = "Column generated automatically by the OSM importer";

        public static final String COMMENTED_METADATA_REGEX = "(\\/\\/)(.?)(out meta)";
        public static final String COMMENTED_CENTER_REGEX = "(\\/\\/)(.?)(out)(.*)(center)";
        public static final String CONTAINS_CENTER_REGEX = "(out)(.*)(center)";
    }

    public static final List<String> METADATA_TAGS = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("@uid");
                add("@version");
                add("@timestamp");
                add("@changeset");
                add("@user");
                add("@visible");
            }}
    );
}



