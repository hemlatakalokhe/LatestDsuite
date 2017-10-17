package de.bonprix.module.styles;

 

import java.util.ArrayList;
import java.util.List;

import org.vaadin.teemusa.gridextensions.client.tableselection.TableSelectionState.TableSelectionMode;

import com.fasterxml.jackson.core.sym.Name;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Item;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.module.styles.StylesView.StylesPresenter;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNotificationProvider;
 
 

@SpringView(name = StylesViewImpl.VIEW_NAME, ui = {VaadinUI.class }, isDefault = false, order = 85)
public class StylesViewImpl extends AbstractMvpView<StylesPresenter> implements StylesView {

	 

	public static final String VIEW_NAME = "Stylesd";

	private CheckBox checkBox;
	
	private BeanItemComboBox<Country>countryCombo;
	
	private BeanItemGrid<Style>styleGrid;
	 
	private Button click,bindSave,edit;
	
	private TextField style_no,style_id,style_desc;
	
	private Tree styleTree;
	
	private BeanItemContainer<Style>styleData;
	
	@javax.annotation.Resource
	private UiNotificationProvider notificationProvider;

	private List<Style> styles;

	private FieldGroup fieldGroup;
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void initializeUI() {
	
		
		this.styleGrid=new BeanItemGrid<>(Style.class);
		this.style_no = FluentUI.textField().caption("Styleno").get(); 
		this.style_desc = FluentUI.textField().caption("StyleDesc").get(); 
		this.styles=new ArrayList<Style>();
		//this.fieldGroup=(FieldGroup) styleGrid.getSelectedRow();
		this.styleTree=new Tree();
	
	styleGrid.setColumns("country","items","styleNo");
	//styleGrid.setSelectionMode(SelectionMode.MULTI);
	styleGrid.setSelectionMode(TableSelectionMode.SINGLE);
	
	// Have a bean
	Style bean = new Style("Mung bean","Abv");

	// Form for editing the bean
	//final BeanFieldGroup<Style> binder = new BeanFieldGroup<Style>(Style.class);
	//binder.setItemDataSource(bean);
	 
	click=new Button("Click");
	bindSave=new Button("bind");
	//
	
	final BeanFieldGroup<Style> binder =
	        new BeanFieldGroup<Style>(Style.class);
	TextField Name=new TextField();
	TextField CName=new TextField();
	TextField Age=new TextField();
	
	styleGrid.setEditorBuffered(true);
	styleGrid.setEditorEnabled(true);
	styleGrid.setEditorSaveCaption("save");
		
	fieldGroup=new FieldGroup();
	final BeanFieldGroup<Style> fieldGroups =
	        new BeanFieldGroup<Style>(Style.class);
	
	click.addClickListener(e->{
		//binder.setItemDataSource((Style) styleGrid.getSelectedItem());
		styleGrid.setEditorFieldGroup(binder);
		fieldGroup.bind(CName, "styleNo");
		fieldGroups.bind(Age, "id");
		
		Object id=styleGrid.select(CName);
		System.out.println("id is"+id.toString());
		
		System.out.println(	"enable"+styleGrid.isEditorEnabled());
		System.out.println(	"active"+styleGrid.isEditorActive());
		System.out.println(" buffer"+	styleGrid.isEditorBuffered());
	 
	//	styleGrid.addItemClickListener(e5->binder.bind(binder.getField("styleNo"), "styleNo"));
		
		
		// binder.bind(Name,"desc");
		// binder.getField(styleGrid).commit();

		//bindSave.addClickListener(e6->
		//{
		///
		//	 try {
			//	binder.commit();
		//	} catch (CommitException e2) {
				// TODO Auto-generated catch block
			//	e2.printStackTrace();
			//}
		//}
	  //  )
	   //  ;
	 
	//binder.buildAndBind("Age", "styleNo");
	});
	 

	// Buffer the form content
//	binder.setBuffered(true);
	/* Button button = new Button("OK", new ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
	        try {
	            binder.commit();
	        } catch (CommitException e) {
	        }
	    }
	}););*/
	
	/* Button button=FluentUI.button().caption("OK").onClick(e1->
	 binder.commit());
	 */
	/*Style bean = new Style("Mung bean", "Adf");
	 *  binder.commit().get());
	BeanItem<Style> item = new BeanItem<Style> (bean);

	// Create an editor bound to a bean field
	TextField firstName = new TextField("First Name",
	        item.getItemProperty("desc"));

	// Add the bean validator
	firstName.addValidator(new BeanValidator(Style.class, "desc"));

	firstName.setImmediate(true);*/
	
	this.countryCombo=new BeanItemComboBox<>(Country.class);
	 
	 
	 Button save=FluentUI.button().caption("SAVE").onClick(e1->
	 save(e1)).get();
	 
	 Button update=FluentUI.button().caption("UPDATE").onClick(e1->
	 save(e1)).get();

	 
	
	setCompositionRoot(FluentUI.horizontal().add(styleGrid,styleTree).
			add(FluentUI.vertical().add(style_desc,style_no,countryCombo,Name,CName,Age
					/*binder.getField("Name")*//*.buildAndBind("Name", "desc"),binder.buildAndBind("Age", "styleNo")*/,click,bindSave).
					add(FluentUI.horizontal().add(update,save).get()).spacing(true).get()).
			get());
	setSizeFull();
	
	
	/* final Window window = new Window("Edit address");
     TextArea street = new TextArea("Street address:");
   
     TextField city = new TextField("City:");
     TextField country = new TextField("Country:");
    

     fieldGroup = new BeanFieldGroup<Style>(Style.class);
     fieldGroup.bind(street, "street");
     fieldGroup.bind(zip, "zip");
     
     fieldGroup.bind(city, "city");
     fieldGroup.bind(country, "country");
     
     
     
     Button button = new Button("Open address editor");*/
     /*, new ClickListener() {

         public void buttonClick(ClickEvent event) {
             getUI().addWindow(window);
         }
     });*/
   /*  window.addCloseListener(new CloseListener() {
         public void windowClose(CloseEvent e) {
             try {
                 fieldGroup.commit();
             } catch (CommitException ex) {
                 ex.printStackTrace();
             }
         }
     });

     window.center();
     window.setWidth(null)*/;
      	
		/*styleData=new BeanItemContainer<>(Style.class);
		styleTree.addItem("Item");
       	 styleTree.addItem("ItemSize");
       	 styleTree.setChildrenAllowed("ItemSize", true);
       	 styleTree.setParent("ItemSize","Item");
       	 Long id=109l;
       	 
       styleTree.addExpandListener(e ->getPresenter().add(id));
       
    
	   styleTree.setCaption("Style");*/
	 // setCompositionRoot(FluentUI.vertical().add(styleTree).get());

		
		 
		
		/* Button update=FluentUI.button().caption("UPDATE").
				 onClick(e1->getPresenter().addcomponent( fieldGroup.buildAndBind("styleDesc", styleDesc));*/
				 
				// FieldGroup fieldGroup=new FieldGroup(new BeanItem<>(Style.class));
				;
		 

		//setCompositionRoot(FluentUI.vertical().add(FluentUI.horizontal().add(style_desc,style_no,countryCombo).sizeFull().
				// add(FluentUI.horizontal().add(save,update,styleTree).spacing(true).get()).get()).add(styleGrid).get());
				
			/*	// Have an item
				PropertysetItem item = new PropertysetItem();
				item.addItemProperty("name", new ObjectProperty<String>("Zaphod"));
				item.addItemProperty("age", new ObjectProperty<Integer>(42));
	 
				TextField nameField = new TextField("Name");
				TextField ageField = new TextField("Age");
				
				// Now create the binder and bind the fields
				FieldGroup binder = new FieldGroup(item);
				binder.bind(nameField, "name");
				binder.bind(ageField, "age");
	    */
				

			
			 
				
				  
				
				
				
				// Have a bean
//				Style bean = new Style("Mung bean","ABC");
//
//				// Form for editing the bean
//				final BeanFieldGroup<Style> binders =
//				        new BeanFieldGroup<Style>(Style.class);
//				binders.setItemDataSource(bean);
//			//	layout.addComponent(binder.buildAndBind("Name", "name"));
//				//layout.addComponent(binder.buildAndBind("Age", "age"));
//
//				// Buffer the form content
//				binders.setBuffered(true);
//				
				this.click=new Button("Ok"); 
				 
				
		
		 
			}

	private void save(ClickEvent e) {
		getPresenter().addcomponent(style_no.getValue(),style_desc.getValue(),
				countryCombo.getSelectedItem());
	}

	@Override	public void checkCheckBox(NavigationRequest request) {
		 
	}

	 
	@Override
		public void shownotification(String message) {
			
			notificationProvider.showInfoNotification(message);
			
		}
 
	@Override
	public void setAllBean(List<Country> beans,List<Style>beanstyle) {
		this.countryCombo.addAllBeans(beans);
		this.styles=beanstyle;
		this.styleGrid.addAllBeans(beanstyle);
		
		
		for(Style stye:styles)
		{
			styleTree.addItem(stye.getStyleNo());
			styleTree.setChildrenAllowed(stye.getStyleNo(),true);

			if(!(stye.getItems().isEmpty()))
			{
				for(Item items:stye.getItems())
				{
					
					styleTree.addItem(items.getColor());
					styleTree.setChildrenAllowed(items.getColor(), false);
					styleTree.setParent(items.getColor(), stye.getStyleNo());
				}
			}
			else
			{
				styleTree.setChildrenAllowed(stye.getStyleNo(),false);
			}
			styleTree.expandItem(stye.getStyleNo());
		}
		
     }

	@Override
	public void addFields() {
		
		
	}

	 
	 

}