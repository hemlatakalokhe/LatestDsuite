package de.bonprix.common.dummydata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.gwt.i18n.server.testing.Gender;
import com.vaadin.server.FontAwesome;

import de.bonprix.model.Inhabitants;
import de.bonprix.model.Inhabitants.Country;
import de.bonprix.model.enums.GridStatus;

public class GridDummyData {

	public static final List<String> FEMALES = Arrays
		.asList("AMELIA", "OLIVIA", "JESSICA", "EMILY", "LILY", "AVA", "MIA", "ISLA", "SOPHIE", "ISABELLA", "EVIE",
				"RUBY", "POPPY", "GRACE", "SOPHIA", "CHLOE", "ISABELLE", "ELLA", "FREYA", "CHARLOTTE", "SCARLETT",
				"DAISY", "LOLA", "EVA", "HOLLY", "MILLIE", "LUCY", "PHOEBE", "LAYLA", "MAISIE", "SIENNA", "ALICE",
				"LILLY", "FLORENCE", "ELLIE", "ERIN", "IMOGEN", "ELIZABETH", "MOLLY", "SUMMER", "MEGAN", "HANNAH",
				"SOFIA", "ABIGAIL", "JASMINE", "LEXI", "MATILDA", "ROSIE", "LACEY", "EMMA", "AMELIE", "GRACIE", "MAYA",
				"HOLLIE", "GEORGIA", "EMILIA", "EVELYN", "BELLA", "BROOKE", "AMBER", "ELIZA", "AMY", "ELEANOR", "LEAH",
				"ESME", "KATIE", "HARRIET", "ANNA", "WILLOW", "ELSIE", "ZARA", "ANNABELLE", "BETHANY", "FAITH",
				"MADISON", "ISABEL", "MARTHA", "ROSE", "JULIA", "PAIGE", "MARYAM", "MADDISON", "HEIDI", "MOLLIE",
				"NIAMH", "SKYE", "AISHA", "IVY", "DARCEY", "FRANCESCA", "ZOE", "KEIRA", "TILLY", "MARIA", "SARAH",
				"LYDIA", "CAITLIN", "ISOBEL", "SARA", "VIOLET");

	public static final List<String> MALES = Arrays
		.asList("OLIVER", "JACK", "CHARLIE", "JACOB", "THOMAS", "ALFIE", "RILEY", "WILLIAM", "JAMES", "JOSHUA",
				"GEORGE", "ETHAN", "NOAH", "SAMUEL", "DANIEL", "OSCAR", "MAX", "MUHAMMAD", "LEO", "TYLER", "JOSEPH",
				"ARCHIE", "HENRY", "LUCAS", "MOHAMMED", "ALEXANDER", "DYLAN", "LOGAN", "ISAAC", "MASON", "BENJAMIN",
				"JAKE", "FINLEY", "HARRISON", "EDWARD", "JAYDEN", "FREDDIE", "ADAM", "ZACHARY", "SEBASTIAN", "RYAN",
				"LEWIS", "THEO", "LUKE", "HARLEY", "MATTHEW", "HARVEY", "TOBY", "LIAM", "CALLUM", "ARTHUR", "MICHAEL",
				"JENSON", "TOMMY", "NATHAN", "BOBBY", "CONNOR", "DAVID", "MOHAMMAD", "LUCA", "CHARLES", "KAI", "JAMIE",
				"ALEX", "BLAKE", "FRANKIE", "REUBEN", "AARON", "DEXTER", "JUDE", "LEON", "OLLIE", "STANLEY", "ELLIOT",
				"GABRIEL", "CAMERON", "OWEN", "LOUIE", "AIDEN", "LOUIS", "ELIJAH", "FINLAY", "RHYS", "CALEB", "EVAN",
				"FREDERICK", "HUGO", "KIAN", "SONNY", "SETH", "KAYDEN", "TAYLOR", "KYLE", "ELLIOTT", "ROBERT",
				"THEODORE", "BAILEY", "RORY", "ELLIS");
	public static final List<Country> COUNTRIES = Arrays
		.asList(new Country(1L, "Afghanistan"), new Country(2L, "Aland Islands"), new Country(3L, "Albania"),
				new Country(4L, "Algeria"), new Country(5L, "American Samoa"), new Country(6L, "Andorra"),
				new Country(7L, "Angola"), new Country(8L, "Anguilla"), new Country(9L, "Antigua and Barbuda"),
				new Country(10L, "Armenia"), new Country(11L, "Aruba"), new Country(12L, "Australia"),
				new Country(13L, "Austria"), new Country(14L, "Azerbaijan"), new Country(15L, "Bahamas"),
				new Country(16L, "Bahrain"), new Country(17L, "Bangladesh"), new Country(18L, "Barbados"),
				new Country(19L, "Belarus"), new Country(20L, "Belgium"), new Country(21L, "Belize"),
				new Country(22L, "Bermuda"), new Country(23L, "Bhutan"),
				new Country(24L, "Bolivia, Plurinational State of"), new Country(25L, "Bosnia and Herzegovina"),
				new Country(26L, "Botswana"), new Country(27L, "Brazil"));

	public static final List<String> IMAGES = Arrays
		.asList("http://digistyle.bonprix.net/thumb/1/8/5/6/prev_14081856.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/6/3/4/9/15066349.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/8/6/0/7/15098607.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/7/1/1/7/13057117.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/2/4/3/7/15092437.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/4/1/3/3/15094133.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/5/0/1/1/14075011.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/4/3/7/8/15094378.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/7/9/1/8/15097918.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/6/3/5/7/15096357.jpg",
				"http://rfp.laudert.de/previews/assets/prev_s/9/6/0/3/15089603.jpg");

	private GridDummyData() {
		// private constructor to hide the implicit public one
	}

	public static final List<Inhabitants> genInhabitants(final int quantity) {
		final List<Inhabitants> result = new ArrayList<>();

		for (long x = 1; x <= quantity; x++) {
			result.add(GridDummyData.genInhabitant(x));
		}

		return result;
	}

	public static final Inhabitants genInhabitant(final long id) {
		Random r = new Random();
		final Inhabitants inh = new Inhabitants(id, Math.random() > 0.5 ? Gender.FEMALE : Gender.MALE);
		if (inh.getGender()
			.equals(Gender.MALE)) {
			inh.setName(GridDummyData.randomOfList(GridDummyData.MALES));
		} else {
			inh.setName(GridDummyData.randomOfList(GridDummyData.FEMALES));
		}
		inh.setBirthday(new Date(
				new Date().getTime() - ((long) (Math.random() * 10000) % (365 * 90)) * TimeUnit.DAYS.toMillis(1)));
		inh.setDateTime(new Date(
				new Date().getTime() - ((long) (Math.random() * 10000) % (365 * 90)) * TimeUnit.DAYS.toMillis(1)));

		inh.setBodySize(1.6 + (Math.random() * (Math.random() > 0.5 ? -1 : +1)));
		inh.setCountry(GridDummyData.randomOfList(GridDummyData.COUNTRIES));
		inh.setVisitedCountries(new HashSet<Country>(
				Arrays.asList(	GridDummyData.randomOfList(GridDummyData.COUNTRIES),
								GridDummyData.randomOfList(GridDummyData.COUNTRIES))));
		inh.setOnFacebook(Math.random() > 0.5);
		inh.setImageUrl(GridDummyData.randomOfList(GridDummyData.IMAGES));
		inh.setStatus(GridDummyData.getCorrectStaus((int) ((r.nextInt(3)) + 1)));
		inh.setIcon(GridDummyData.randomOfList(Arrays.asList(FontAwesome.values())));
		inh.setChecked(Math.round(Math.random() * 100) % 2 == 0);

		return inh;
	}

	private static final <T> T randomOfList(final List<T> list) {
		Random r = new Random();

		return list.get((r.nextInt(10000)) % (list.size() - 1));
	}

	private static GridStatus getCorrectStaus(final int randomInt) {
		GridStatus newStatus = null;
		switch (randomInt) {
		case 1:
			newStatus = GridStatus.CREATED;
			break;
		case 2:
			newStatus = GridStatus.RELEASED;
			break;
		case 3:
			newStatus = GridStatus.CANCELLED;
			break;
		default:
			break;
		}
		return newStatus;
	}

}
