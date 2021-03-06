package org.molgenis.util.test;

import java.io.File;
import java.net.URL;

public abstract class AbstractResourceTest
{
	protected File getTestResource(String name)
	{
		URL resource = this.getClass().getResource(name);
		return new File(resource.getFile());
	}
}
