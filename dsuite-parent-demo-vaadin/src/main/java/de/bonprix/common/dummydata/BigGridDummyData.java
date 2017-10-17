package de.bonprix.common.dummydata;

import java.util.ArrayList;
import java.util.List;
import de.bonprix.model.BigGrid;

public class BigGridDummyData {

	private BigGridDummyData() {
	}

	public static final List<BigGrid> genGridData(final int quantity) {
		final List<BigGrid> result = new ArrayList<>();

		for (int i = 1; i <= quantity; i++) {
			result.add(BigGridDummyData.genValues(i));
		}

		return result;
	}

	public static final BigGrid genValues(int id) {
		final BigGrid bigGrid = new BigGrid();
		bigGrid.setId(id);
		bigGrid.setCol1(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol2(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol3(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol4(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol5(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol6(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol7(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol8(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol9(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol10(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol11(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol12(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol13(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol14(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol15(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol16(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol17(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol18(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol19(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol20(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol21(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol22(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol23(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol24(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol25(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol26(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol27(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol28(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol29(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol30(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol31(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol32(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol33(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol34(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol35(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol36(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol37(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol38(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol39(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol40(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol41(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol42(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol43(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol44(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol45(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol46(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol47(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol48(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol49(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol50(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol51(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol52(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol53(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol54(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol55(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol56(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol57(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol58(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol59(Math.random() > 0.5 ? "X" : "");
		bigGrid.setCol60(Math.random() > 0.5 ? "X" : "");

		return bigGrid;
	}

}