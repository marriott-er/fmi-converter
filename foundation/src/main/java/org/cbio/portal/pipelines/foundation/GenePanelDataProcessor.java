/*
 * Copyright (c) 2017 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.cbio.portal.pipelines.foundation;

import org.cbio.portal.pipelines.foundation.model.CaseType;
import org.cbio.portal.pipelines.foundation.model.staging.GenePanelData;

import java.util.*;
import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author ochoaa
 */
public class GenePanelDataProcessor implements ItemProcessor<CaseType, String> {

    @Override
    public String process(final CaseType caseType) throws Exception {
        GenePanelData genePanelData = new GenePanelData(caseType);
        if (Strings.isNullOrEmpty(genePanelData.getMutationsPanel())) {
            return null;
        }
        List<String> record = new ArrayList();
        Map<String, String> map = genePanelData.getStagingMap();
        for (String field : map.keySet()) {
            String value = genePanelData.getClass().getMethod(map.get(field)).invoke(genePanelData).toString();
            record.add(value);
        }
        return StringUtils.join(record, "\t");
    }

}
