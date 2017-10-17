package de.bonprix.showcase.cases.grid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Component;
import de.bonprix.common.dummydata.BigGridDummyData;
import de.bonprix.model.BigGrid;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;

/**
 * A simple Grid demo with BeanItemContainer where the Grid-Header-Content will
 * get a vertical orientation.
 *
 * @author tmeissne
 *
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class BeanItemGridRotateHeaderContentDemo extends ShowcaseWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanItemGridRotateHeaderContentDemo.class);

	private BeanItemGrid<BigGrid> grid;

	public BeanItemGridRotateHeaderContentDemo() {
		super("GRID_TABLE_TREE", "BEANITEMGRIDROTATEHEADERDEMO");
	}

	@Override
	protected Component createLayout() {

		genGrid();

		return FluentUI.vertical()
			.margin()
			.spacing()
			.sizeFull()
			.spacing()
			.add(this.grid, 0.9f)
			.get();
	}

	private void genGrid() {

		this.grid = new BeanItemGrid<>(
				new BeanItemContainer<BigGrid>(BigGrid.class, BigGridDummyData.genGridData(200)));

		this.grid.setSizeFull();
		this.grid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

		this.grid.setEditorEnabled(false);

		this.grid.setColumns(	"id", "col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "col9", "col10",
								"col11", "col12", "col13", "col14", "col15", "col16", "col17", "col18", "col19",
								"col20", "col21", "col22", "col23", "col24", "col25", "col26", "col27", "col28",
								"col29", "col30", "col31", "col32", "col33", "col34", "col35", "col36", "col37",
								"col38", "col39", "col40", "col41", "col42", "col43", "col44", "col45", "col46",
								"col47", "col48", "col49", "col50", "col51", "col52", "col53", "col54", "col55",
								"col56", "col57", "col58", "col59", "col60");

		rotatedGrid();

	}

	/**
	 * All what the programming needs to do to rotate the header-content of the
	 * grid happens in this method.
	 */
	private void rotatedGrid() {

		/**
		 * Call this method to rotate the header-content of the grid. The header
		 * content is rotated by 90 degrees then
		 */
		this.grid.rotateHeaderContent(60, 32);

		/**
		 * if needed, you can set width of certain columns. here the ID - column
		 * and the Col13 - column.
		 */
		this.grid.getColumn("id")
			.setWidth(60);
		this.grid.getColumn("col13")
			.setWidth(50);

	}

}
