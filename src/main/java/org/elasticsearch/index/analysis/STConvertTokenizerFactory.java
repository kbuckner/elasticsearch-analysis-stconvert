package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @deprecated
 */
@Deprecated
public class STConvertTokenizerFactory extends AbstractTokenizerFactory {

   private String type="t2s";
   private String delimiter=",";
    private Boolean keepBoth=false;

    @Inject
    public STConvertTokenizerFactory(Index index, IndexSettingsService indexSettingsService, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
         type = settings.get("convert_type", "t2s");
         delimiter = settings.get("delimiter", ",");
         String keepBothStr = settings.get("keep_both", "false");
        if(keepBothStr.equals("true")) {
             keepBoth = true;
        }
    }

    @Override
    public Tokenizer create() {
        STConvertType convertType= STConvertType.TRADITIONAL_2_SIMPLE;
        if(type.equals("s2t")){
            convertType = STConvertType.SIMPLE_2_TRADITIONAL;
        }

        return new STConvertTokenizer(convertType, delimiter,keepBoth);
    }
}

