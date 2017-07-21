/*
 * Copyright 2017 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.services.query.app.util;

import org.activiti.services.query.app.specification.builders.BaseSpecificationsBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchTermResolver<T> {

    public Specification<T> applyBuilderToSearchTerm(String search, BaseSpecificationsBuilder<T> specificationsBuilder){
        String operationSetExper = StringUtils.join(Arrays.asList(SearchOperation.SIMPLE_OPERATION_SET),'|');
        Pattern pattern = Pattern.compile("(.*?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            //TODO: (\w+?) pattern is excluding . from group(1) - need to leave it in to support nesting
            specificationsBuilder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        Specification<T> spec = specificationsBuilder.build();
        return spec;
    }
}
