/*
 * Copyright (C) 2014  Camptocamp
 *
 * This file is part of MapFish Print
 *
 * MapFish Print is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MapFish Print is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MapFish Print.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mapfish.print.processor;

import junit.framework.TestCase;

import java.util.Map;
import javax.annotation.Nullable;

/**
 * @author jesseeichar on 3/25/14.
 */
public class ProcessorDependencyGraphTest extends TestCase {
    public void testToString() throws Exception {
        ProcessorDependencyGraph graph = new ProcessorDependencyGraph();
        ProcessorGraphNode root1 = new ProcessorGraphNode(new TestProcessor("root1"), null);
        ProcessorGraphNode root2 = new ProcessorGraphNode(new TestProcessor("root2"), null);
        ProcessorGraphNode dep11 = new ProcessorGraphNode(new TestProcessor("dep11"), null);
        ProcessorGraphNode dep21 = new ProcessorGraphNode(new TestProcessor("dep21"), null);
        ProcessorGraphNode dep11_1 = new ProcessorGraphNode(new TestProcessor("dep11_1"), null);
        ProcessorGraphNode dep11_2 = new ProcessorGraphNode(new TestProcessor("dep11_2"), null);
        graph.addRoot(root1);
        graph.addRoot(root2);

        root1.addDependency(dep11);
        root2.addDependency(dep21);

        dep11.addDependency(dep11_1);
        dep11.addDependency(dep11_2);
        assertEquals("dep11_1", dep11_1.toString());
        assertEquals("dep11\n  +-- dep11_1\n  +-- dep11_2", dep11.toString());
        assertEquals("+ root1\n  +-- dep11\n    +-- dep11_1\n    +-- dep11_2\n+ root2\n  +-- dep21", graph.toString());
    }

    private static class TestProcessor extends AbstractProcessor {
        private final String name;

        private TestProcessor(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Nullable
        @Override
        public Map<String, Object> execute(Map<String, Object> values) throws Exception {
            return null;
        }
    }
}