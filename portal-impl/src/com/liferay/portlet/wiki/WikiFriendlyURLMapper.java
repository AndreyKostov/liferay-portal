/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.wiki;

import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinn Lok
 * @author Levente Hudák
 */
public class WikiFriendlyURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public String buildPath(LiferayPortletURL liferayPortletURL) {
		Map<String, String> routeParameters = new HashMap<String, String>();

		buildRouteParameters(liferayPortletURL, routeParameters);

		addParameter(routeParameters, "nodeName");
		addParameter(routeParameters, "title");

		String friendlyURLPath = router.parametersToUrl(routeParameters);

		if (Validator.isNull(friendlyURLPath)) {
			return null;
		}

		addParametersIncludedInPath(liferayPortletURL, routeParameters);

		friendlyURLPath = StringPool.SLASH.concat(getMapping()).concat(
			friendlyURLPath);

		return friendlyURLPath;
	}

	protected void addParameter(
		Map<String, String> routeParameters, String name) {

		if (!routeParameters.containsKey(name)) {
			return;
		}

		String nodeName = routeParameters.get(name);

		nodeName = StringUtil.replace(
			nodeName, _ESCAPED_CHARS, _UNESCAPED_CHARS);

		routeParameters.put(name, nodeName);
	}

	@Override
	protected void populateParams(
		Map<String, String[]> parameterMap, String namespace,
		Map<String, String> routeParameters) {

		addParameter(routeParameters, "nodeName");
		addParameter(routeParameters, "title");

		super.populateParams(parameterMap, namespace, routeParameters);
	}

	private static final String[] _ESCAPED_CHARS = new String[] {
		"<PLUS>", "<QUESTION>", "<SLASH>"
	};

	private static final String[] _UNESCAPED_CHARS = new String[] {
		StringPool.PLUS, StringPool.QUESTION, StringPool.SLASH
	};

}