package org.molgenis.framework.ui.commands;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.molgenis.framework.db.Database;
import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.framework.ui.FormController;
import org.molgenis.framework.ui.FormModel;
import org.molgenis.framework.ui.ScreenModel;
import org.molgenis.framework.ui.html.ActionInput;
import org.molgenis.framework.ui.html.HtmlInput;
import org.molgenis.model.MolgenisModelException;
import org.molgenis.util.Entity;
import org.molgenis.util.Tuple;
import org.molgenis.util.XlsWriter;

public class DownloadAllXlsCommand<E extends Entity> extends SimpleCommand
{
	private static final long serialVersionUID = -2682113764135477871L;
	public static final transient Logger logger = Logger.getLogger(DownloadAllCommand.class);

	public DownloadAllXlsCommand(String name, FormController<E> parentScreen)
	{
		super(name, parentScreen);
		this.setLabel("Download all (.xls)");
		this.setIcon("generated-res/img/download.png");
		this.setDownload(true);
		this.setMenu("File");
	}

	@Override
	public ScreenModel.Show handleRequest(Database db, Tuple request, OutputStream xlsDownload) throws Exception
	{
		logger.debug(this.getName());

		FormModel<? extends Entity> model = this.getFormScreen();
		FormController<?> controller = ((FormController<?>) this.getController());

		List<String> fieldsToExport = controller.getVisibleColumnNames();

		// TODO : remove entity name, capitals to small , and remove all _name
		// fields
		// we need to rewrite rules to accomodate the 'all'
		QueryRule[] rules;
		try
		{
			rules = controller.rewriteAllRules(db, Arrays.asList(model.getRulesExclLimitOffset()));
		}
		catch (MolgenisModelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DatabaseException(e);
		}

		// Comments from Despoina:
		// TODO : the actual xls headers/formatting
		// TODO : this needs different call or TODO just an extra if in
		// abstractMolgenisServlet for the different suffix (.xls) ?

		// This db.find() is rerouted by your Database implementation's find()
		// to the one in the appropriate mapper
		db.find(model.getController().getEntityClass(), new XlsWriter(xlsDownload), fieldsToExport, rules);

		return ScreenModel.Show.SHOW_MAIN;
	}

	public List<ActionInput> getActions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HtmlInput<?>> getInputs() throws DatabaseException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
