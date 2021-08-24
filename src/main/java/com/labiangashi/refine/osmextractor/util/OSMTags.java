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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OSMTags {
    public static final Set<String> MAIN_TAGS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("aerialway", "aeroway", "amenity", "barrier",
            "boundary", "building", "craft", "emergency", "ford", "geological", "highway", "historic", "landuse", "leisure", "man_made", "military", "natural",
            "office", "place", "power", "public_transport", "railway", "shop", "tourism", "waterway", "type", "entrance", "pipeline", "healthcare",
            "playground", "attraction", "traffic_sign", "traffic_sign:forward", "traffic_sign:backward", "golf", "indoor", "cemetry", "building:part",
            "landcover", "advertising", "traffic_calming", "club", "cemetery", "police", "telecom")));
}
