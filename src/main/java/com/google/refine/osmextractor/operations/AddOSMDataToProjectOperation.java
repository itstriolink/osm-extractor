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

package com.google.refine.osmextractor.operations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.refine.browsing.Engine;
import com.google.refine.browsing.EngineConfig;
import com.google.refine.model.*;
import com.google.refine.operations.EngineDependentOperation;
import com.google.refine.process.LongRunningProcess;
import com.google.refine.process.Process;
import com.google.refine.util.ParsingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class AddOSMDataToProjectOperation extends EngineDependentOperation {
    static final Logger logger = LoggerFactory.getLogger(AddOSMDataToProjectOperation.class);
    final protected ArrayNode _mappings;
    final protected ArrayNode _data;

    @JsonCreator
    public AddOSMDataToProjectOperation(
            @JsonProperty("engineConfig")
                    EngineConfig engineConfig,
            @JsonProperty("mappings")
                    String mappings,
            @JsonProperty("data")
                    String data
    ) {
        super(engineConfig);
        _mappings = ParsingUtilities.evaluateJsonStringToArrayNode(mappings);
        _data = ParsingUtilities.evaluateJsonStringToArrayNode(data);
    }

    @Override
    protected String getBriefDescription(Project project) {
        return "Adding OpenStreetMap data...";
    }

    @Override
    public Process createProcess(Project project, Properties options)
            throws Exception {
        Engine engine = createEngine(project);
        engine.initializeFromConfig(_engineConfig);

        return new AddOSMDataToProjectProcess(
                project,
                engine,
                _mappings,
                _data,
                getBriefDescription(project));
    }

    public class AddOSMDataToProjectProcess extends LongRunningProcess implements Runnable {

        protected Project _project;
        protected Engine _engine;
        protected ArrayNode _mappings;
        protected ArrayNode _data;

        protected AddOSMDataToProjectProcess(Project project, Engine engine, ArrayNode mappings, ArrayNode data, String description) {
            super(description);
            _project = project;
            _engine = engine;
            _mappings = mappings;
            _data = data;
        }

        @Override
        protected Runnable getRunnable() {
            return this;
        }

        @Override
        public void run() {
            if (_mappings != null && _mappings.size() > 0) {
                for (int i = 0; i < _mappings.size(); i++) {
                    if (_mappings.get(i) instanceof ObjectNode) {
                        ObjectNode obj = (ObjectNode) _mappings.get(i);
                        String newColumnName = obj.get("newColumnName").asText();

                        if (newColumnName != null && !newColumnName.trim().isEmpty()) {
                            if (_project.columnModel.getColumnByName(newColumnName) != null) {
                                _project.processManager.onFailedProcess(this, new Exception("Another column is already named " + newColumnName));
                                return;
                            }

                            try {
                                if (_data != null && _data.size() > 0) {
                                    Column column = new Column(_project.columnModel.allocateNewCellIndex(), newColumnName);
                                    _project.columnModel.addColumn(
                                            _project.columnModel.columns.size(),
                                            column,
                                            false
                                    );

                                    for (int j = 0; j < _data.size(); j++) {
                                        if (_mappings.get(i) instanceof ObjectNode) {
                                            ObjectNode node = (ObjectNode) _data.get(j);
                                            ObjectNode tags = node.get("tags").deepCopy();
                                            String osmTag = obj.get("osmTag").asText();
                                            String value = null;

                                            if (tags.has(osmTag)) {
                                                value = tags.get(osmTag).asText();
                                            }

                                            Row row;
                                            try {
                                                row = _project.rows.get(j);
                                            } catch (IndexOutOfBoundsException e) {
                                                Row _row = new Row(_project.columnModel.getMaxCellIndex() + 1);
                                                _project.rows.add(_row);
                                                row = _row;
                                            }

                                            row.setCell(column.getCellIndex(), new Cell(value, null));
                                        }
                                    }
                                }
                            } catch (ModelException e) {
                                logger.error("Couldn't add column.", e);
                            }
                        }
                    }


                    _progress = (i + 1) * 100 / _mappings.size();
                    if (_canceled) {
                        break;
                    }
                }
            }

            _project.processManager.onDoneProcess(this);
        }
    }
}
