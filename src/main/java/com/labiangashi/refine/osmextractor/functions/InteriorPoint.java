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

package com.labiangashi.refine.osmextractor.functions;

import com.google.refine.expr.EvalError;
import com.google.refine.grel.ControlFunctionRegistry;
import com.google.refine.grel.Function;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

import java.util.Properties;

public class InteriorPoint implements Function {
    public Object call(Properties bindings, Object[] args) {
        if (args.length == 1 && args[0] != null && args[0] instanceof String) {
            try {
                Geometry geometry = new WKTReader().read((String) args[0]);

                Point point = org.locationtech.jts.algorithm.InteriorPoint.getInteriorPoint(geometry);

                return new WKTWriter().writeFormatted(point);
            } catch (ParseException e) {
                return new EvalError(
                        ControlFunctionRegistry.getFunctionName(this) +
                                ": ParseException: " +
                                e.getMessage()
                );
            }
        }

        return new EvalError(ControlFunctionRegistry.getFunctionName(this) + " expects 1 argument");
    }

    @Override
    public String getDescription() {
        return "Returns the interior point of a WKT string";
    }

    @Override
    public String getParams() {
        return "string wkt";
    }

    @Override
    public String getReturns() {
        return "string";
    }
}
