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

package com.google.refine.osmextractor.extractor;

import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import org.locationtech.jts.geom.Geometry;

import java.util.Map;

public class OSMElement {

    private Geometry geometry;
    private long id;
    private Map<String, String> tags;
    private OsmMetadata metadata;

    public OSMElement(Geometry geometry, long id, Map<String, String> tags, OsmMetadata metadata) {
        this.geometry = geometry;
        this.id = id;
        this.tags = tags;
        this.metadata = metadata;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public OsmMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(OsmMetadata metadata) {
        this.metadata = metadata;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
