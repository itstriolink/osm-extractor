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

package com.labiangashi.refine.osmextractor.extractor;

import de.topobyte.osm4j.core.access.OsmInputException;
import de.topobyte.osm4j.core.access.OsmReader;
import de.topobyte.osm4j.core.dataset.InMemoryMapDataSet;
import de.topobyte.osm4j.core.dataset.MapDataSetLoader;
import de.topobyte.osm4j.core.model.iface.OsmMetadata;
import de.topobyte.osm4j.core.model.iface.OsmRelation;
import de.topobyte.osm4j.core.model.iface.OsmWay;
import de.topobyte.osm4j.core.resolve.EntityNotFoundException;
import de.topobyte.osm4j.geometry.RegionBuilder;
import de.topobyte.osm4j.geometry.RegionBuilderResult;
import de.topobyte.osm4j.geometry.WayBuilder;
import de.topobyte.osm4j.geometry.WayBuilderResult;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.WKTWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class OSMExtractor {
    private List<OSMElement> points;
    private List<OSMElement> lineStrings;
    private List<OSMElement> multiLineStrings;
    private List<OSMElement> multiPolygons;

    private final GeometryFactory geometryFactory;
    private final WayBuilder wayBuilder;
    private final RegionBuilder regionBuilder;
    private final WKTWriter wktWriter;
    private String overpassInstance;
    private String overpassQuery;
    private boolean includeMetadata;

    private InMemoryMapDataSet data;


    public OSMExtractor() {
        this.points = new ArrayList<>();
        this.lineStrings = new ArrayList<>();
        this.multiLineStrings = new ArrayList<>();
        this.multiPolygons = new ArrayList<>();

        includeMetadata = false;

        this.geometryFactory = new GeometryFactory();
        this.wayBuilder = new WayBuilder();
        this.regionBuilder = new RegionBuilder();
        this.wktWriter = new WKTWriter();
    }

    public String getOverpassInstance() {
        return overpassInstance;
    }

    public void setOverpassInstance(String overpassInstance) {
        this.overpassInstance = overpassInstance;
    }

    public boolean getIncludeMetadata() {
        return includeMetadata;
    }

    public void setIncludeMetadata(boolean includeMetadata) {
        this.includeMetadata = includeMetadata;
    }

    public void setWKTPrecisionScale(double precisionScale) {
        this.wktWriter.setPrecisionModel(new PrecisionModel(precisionScale));
    }

    public String getOverpassQuery() {
        return overpassQuery;
    }

    public void setOverpassQuery(String overpassQuery) {
        this.overpassQuery = overpassQuery;
    }

    public List<OSMElement> getPoints() {
        return points;
    }

    public List<OSMElement> getLineStrings() {
        return lineStrings;
    }

    public List<OSMElement> getMultiLineStrings() {
        return multiLineStrings;
    }

    public List<OSMElement> getMultiPolygons() {
        return multiPolygons;
    }

    public void addPoint(Point point, long id, Map<String, String> tags, OsmMetadata metadata) {
        this.points.add(new OSMElement(point, id, tags, metadata));
    }

    public void addLineString(LineString lineString, long id, Map<String, String> tags, OsmMetadata metadata) {
        this.lineStrings.add(new OSMElement(lineString, id, tags, metadata));
    }

    public void addMultiLineString(MultiLineString multiLineString, long id, Map<String, String> tags, OsmMetadata metadata) {
        this.multiLineStrings.add(new OSMElement(multiLineString, id, tags, metadata));
    }

    public void addMultiPolygon(MultiPolygon multiPolygon, long id, Map<String, String> tags, OsmMetadata metadata) {
        this.multiPolygons.add(new OSMElement(multiPolygon, id, tags, metadata));
    }

    public InMemoryMapDataSet loadData(OsmReader reader, boolean resetGeometry) throws IOException, OsmInputException {
        this.data = MapDataSetLoader.read(reader, true, true, true);

        if (resetGeometry) {
            resetGeometry();
        }

        return this.data;
    }

    public int getPointsSize() {
        return this.points.size();
    }

    public int getLineStringsSize() {
        return this.lineStrings.size();
    }

    public int getMultiLineStringsSize() { return this.multiLineStrings.size(); }

    public int getMultiPolygonsSize() {
        return this.multiPolygons.size();
    }

    public MultiLineString buildMultiLineString(LineString[] lineStrings) {
        return geometryFactory.createMultiLineString(lineStrings);
    }

    public String getWKTRepresentation(Geometry g) {
        return wktWriter.write(g);
    }

    public Collection<LineString> getLine(OsmWay way) {
        List<LineString> results = new ArrayList<>();
        try {
            WayBuilderResult lines = wayBuilder.build(way, data);
            results.addAll(lines.getLineStrings());
            if (lines.getLinearRing() != null && !lines.getLinearRing().isEmpty()) {
                results.add(lines.getLinearRing());
            }
        } catch (EntityNotFoundException e) {
            // ignore
        }
        return results;
    }

    public List<LineString> getLines(OsmRelation relation) {
        List<LineString> results = new ArrayList<>();

        try {
            RegionBuilderResult lines = regionBuilder.build(relation, data);
            results.addAll(lines.getLineStrings());
        } catch (EntityNotFoundException e) {
            // ignore
        }
        return results;
    }

    public MultiPolygon getPolygon(OsmWay way) {
        try {
            RegionBuilderResult region = regionBuilder.build(way, data);
            return region.getMultiPolygon();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public MultiPolygon getPolygon(OsmRelation relation) {
        try {
            RegionBuilderResult region = regionBuilder.build(relation, data);
            return region.getMultiPolygon();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    private void resetGeometry() {
        this.points = new ArrayList<>();
        this.lineStrings = new ArrayList<>();
        this.multiLineStrings = new ArrayList<>();
        this.multiPolygons = new ArrayList<>();
    }
}
