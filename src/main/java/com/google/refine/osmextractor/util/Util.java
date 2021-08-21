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

package com.google.refine.osmextractor.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static boolean isValidOverpassQuery(String query) {
        return !query.contains("[out:json]");
    }

    public static boolean overpassQueryContainsMetadata(String query) {
        Pattern pattern = Pattern.compile(Constants.Importing.COMMENTED_METADATA_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);
        boolean matchFound = matcher.find();

        return !matchFound && query.contains("out meta");

    }

    public static boolean overpassQueryContainsOutCenter(String query) {
        Pattern pattern = Pattern.compile(Constants.Importing.COMMENTED_CENTER_REGEX, Pattern.CASE_INSENSITIVE);
        Pattern pattern2 = Pattern.compile(Constants.Importing.CONTAINS_CENTER_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);
        Matcher matcher2 = pattern2.matcher(query);
        boolean matchFound = matcher.find();
        boolean matchFound2 = matcher2.find();

        return !matchFound && matchFound2;
    }
}
