/**
 * 
 */
package org.openxava.web.layout.impl;

import static org.openxava.web.layout.LayoutJspKeys.ATTRVAL_STYLE_WIDTH_100P;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_BORDER;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_CELL_PADDING;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_CELL_SPACING;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_CLASS;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_COLSPAN;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_ID;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_LIST;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_SRC;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_STYLE;
import static org.openxava.web.layout.LayoutJspKeys.TAG_DIV;
import static org.openxava.web.layout.LayoutJspKeys.TAG_IMG;
import static org.openxava.web.layout.LayoutJspKeys.TAG_SPAN;
import static org.openxava.web.layout.LayoutJspKeys.TAG_TABLE;
import static org.openxava.web.layout.LayoutJspKeys.TAG_TD;
import static org.openxava.web.layout.LayoutJspKeys.TAG_TR;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openxava.annotations.LabelFormatType;
import org.openxava.controller.ModuleContext;
import org.openxava.model.meta.MetaReference;
import org.openxava.util.Is;
import org.openxava.view.View;
import org.openxava.view.meta.MetaView;
import org.openxava.view.meta.MetaViewAction;
import org.openxava.web.Ids;
import org.openxava.web.layout.AbstractJspPainter;
import org.openxava.web.layout.ILayoutCollectionBeginElement;
import org.openxava.web.layout.ILayoutCollectionEndElement;
import org.openxava.web.layout.ILayoutColumnBeginElement;
import org.openxava.web.layout.ILayoutColumnEndElement;
import org.openxava.web.layout.ILayoutFrameBeginElement;
import org.openxava.web.layout.ILayoutFrameEndElement;
import org.openxava.web.layout.ILayoutGroupBeginElement;
import org.openxava.web.layout.ILayoutGroupEndElement;
import org.openxava.web.layout.ILayoutPropertyBeginElement;
import org.openxava.web.layout.ILayoutPropertyEndElement;
import org.openxava.web.layout.ILayoutRowBeginElement;
import org.openxava.web.layout.ILayoutRowEndElement;
import org.openxava.web.layout.ILayoutSectionsBeginElement;
import org.openxava.web.layout.ILayoutSectionsEndElement;
import org.openxava.web.layout.ILayoutSectionsRenderBeginElement;
import org.openxava.web.layout.ILayoutSectionsRenderEndElement;
import org.openxava.web.layout.ILayoutViewBeginElement;
import org.openxava.web.layout.ILayoutViewEndElement;
import org.openxava.web.layout.LayoutJspKeys;
import org.openxava.web.layout.LayoutJspUtils;
import org.openxava.web.style.Style;
import org.openxava.web.taglib.ActionTag;
import org.openxava.web.taglib.EditorTag;
import org.openxava.web.taglib.LinkTag;

/**
 * Implements a basic Painter.
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutPainter extends AbstractJspPainter {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(DefaultLayoutPainter.class);
	private boolean firstCellPainted = false;
	private boolean smallLabelPainted = false;
	private int tdPerColumn = 2; // One TD for the label and another for Data and other cells.
	private boolean blockStarted = false;
	
	/**
	 * @see org.openxava.web.layout.ILayoutPainter#beginView(org.openxava.web.layout.LayoutElement)
	 */
	public void beginView(ILayoutViewBeginElement element) {
		resetLog();
		setContainer(element);
		attributes.clear();
		if (element.isRepresentsSection() &&
				sectionFullWidthFrames(element.getView())) {
			attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
		} else {
			if (viewFullWidthFrames(element.getView())
					//&& element.getMaxFramesCount() > 0 // Removed for simple properties bug #
					) {
				attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
			}
		}
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutContent());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#endView(org.openxava.web.layout.LayoutElement)
	 */
	public void endView(ILayoutViewEndElement element) {
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		outputLog();
	}

	/**
	 * This has the same behavior as the startFrame method.
	 * @see org.openxava.web.layout.ILayoutPainter#beginGroup(org.openxava.web.layout.LayoutElement)
	 */
	public void beginGroup(ILayoutGroupBeginElement element) {
		beginFrame(element, "group_");
	}

	/**
	 * This has the same behavior as the endFrame method.
	 * @see org.openxava.web.layout.ILayoutPainter#endGroup(org.openxava.web.layout.LayoutElement)
	 */
	public void endGroup(ILayoutGroupEndElement element) {
		endFrame(element);
	}
	
	public void beginFrame(ILayoutFrameBeginElement element) {
		beginFrame(element, "");
	}

	/**
	 * Creates the frame. This implementation uses the same style as the original OX design.
	 * @see org.openxava.web.layout.ILayoutPainter#beginFrame(org.openxava.web.layout.LayoutElement)
	 */
	public void beginFrame(ILayoutFrameBeginElement element, String suffix) {
		// Frame should occupy as many columns as needed. 
		// In this design each column is 2 TD wide.
		// However if this frame is the only one in the row
		// Takes the full size of the view.
		Integer columnSpan = 0;
		String labelKey = Ids.decorate(
				getRequest().getParameter("application"),
				getRequest().getParameter("module"),
				"label_" + getView().getPropertyPrefix() + element.getName()); 
		int count = getRow().getRowCurrentColumnsCount() + 1;
		if (getRow().getMaxFramesCount() == getRow().getMaxRowColumnsCount() &&
				getRow().getMaxFramesCount() == 1 &&
				element.getGroupLevel() <= 3) { // A single frame topmost
			columnSpan = getMaxColumnsOnView();
			count = getMaxColumnsOnView();
		}
		columnSpan = calculateTdSpan(columnSpan);
		
		getRow().setRowCurrentColumnsCount(count);
		
		attributes.clear();
		if (columnSpan > 0) {
			attributes.put(ATTR_COLSPAN, columnSpan.toString());
		}
		boolean sibling = false;
		if (getRow().getMaxFramesCount() > 1) {
			if (element.getPosition() > 0) {
				sibling = true;
			}
		}

		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
		
		write(getStyle().getFrameHeaderStartDecoration(100, sibling));
			write(getStyle().getFrameTitleStartDecoration());
				attributes.clear();
				attributes.put(ATTR_ID, labelKey);
				write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
					write(element.getLabel());
				write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
			write(getStyle().getFrameTitleEndDecoration());
			write(getStyle().getFrameActionsStartDecoration());
				String frameId = Ids.decorate(getRequest(), "frame_" + suffix + getView().getPropertyPrefix() + element.getName());
				String frameActionsURL = "frameActions.jsp?frameId=" + frameId + 
					"&closed=" + getView().isFrameClosed(frameId);
				includeJspPage(frameActionsURL);
			write(getStyle().getFrameActionsEndDecoration());
		write(getStyle().getFrameHeaderEndDecoration());
		write(getStyle().getFrameContentStartDecoration(frameId + "content", getView().isFrameClosed(frameId)));
		// Start the property container
		attributes.clear();
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutContent());
		if (getContainer().getMaxFramesCount() <= 1) {
			attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
		}
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
		setContainer(element);
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#endFrame(org.openxava.web.layout.LayoutElement)
	 */
	public void endFrame(ILayoutFrameEndElement element) {
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		write(getStyle().getFrameContentEndDecoration());
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		createTdColumnSpan();
		unsetContainer();
	}

	/**
	 * Actually starts a row where all the columns are painted.
	 * @see org.openxava.web.layout.ILayoutPainter#beginRow(org.openxava.web.layout.LayoutElement)
	 */
	public void beginRow(ILayoutRowBeginElement element) {
		setRow(element);
		if (element.getMaxRowColumnsCount() > 0) {
			attributes.clear();
			if (getRow().getMaxFramesCount() > 1) {
				attributes.put("valign", "top");
			}
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TR, attributes));
			if (element.getMaxFramesCount() > 0) {
				attributes.clear();
				Integer columnSpan = calculateTdSpan(getMaxColumnsOnView());
				attributes.put(ATTR_COLSPAN, columnSpan.toString());
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
				
				attributes.clear();
				attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
				attributes.clear();
				attributes.put("valign", "top");
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TR, attributes));
			} else if (element.isBlockStart()) {
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE));
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
				blockStarted = true;
			}
		}
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#endRow(org.openxava.web.layout.LayoutElement)
	 */
	public void endRow(ILayoutRowEndElement element) {
		if (getRow().getMaxRowColumnsCount() > 0 ) {
			if (!getRow().isBlockEnd()) {
				createTdColumnSpan();
			}
			if (getRow().getMaxFramesCount() > 0) {
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			} else if (getRow().isBlockEnd()) {
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
				blockStarted = false;
			}
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
			if (!getRow().isLast()) { // Do not create the row spacer for the last row
				createRowSpacer();
			}
		}
		unsetRow();
	}
	
	/**
	 * Creates a space between rows.
	 */
	protected void createRowSpacer() {
		attributes.clear();
		attributes.put(ATTR_CLASS, getStyle().getLayoutRowSpacer());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TR, attributes));
		
		// Label content
		attributes.clear();
		attributes.put(ATTR_CLASS, getStyle().getLayoutLabelCell());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		
		// Data content
		attributes.clear();
		attributes.put(ATTR_CLASS, getStyle().getLayoutDataCell());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
	}
	
	/**
	 * Creates the necessary td for column span
	 */
	protected void createTdColumnSpan() {
		createTdColumnSpan(getRow().getRowCurrentColumnsCount(), LayoutJspKeys.CHAR_SPACE);
	}

	/**
	 * Creates the necessary td for column span
	 * @param currentColumnsCount Number of columns so far displayed
	 */
	protected void createTdColumnSpan(int currentColumnsCount, String spacer) {
		// Separation line
		int maxColumnsCount = getMaxColumnsOnView();
		if (maxColumnsCount > currentColumnsCount) {
			Integer lastColumnSpan = maxColumnsCount - currentColumnsCount;
			lastColumnSpan = calculateTdSpan(lastColumnSpan); // Each column has 2 TD elements.

			if (lastColumnSpan > 0) {
				attributes.clear();
				attributes.put(ATTR_COLSPAN, lastColumnSpan.toString());
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			}
		}
	}

	
	/**
	 * Each column does not open a table element (TD), this is done by the 
	 * startCell method. However, each column can contain more than one cell,
	 * but only three TD elements are allowed in the column, so the first cell
	 * creates one TD for the left spacer, another TD for the label and 
	 * a final TD for the data and any other remaining cell of the column.
	 * By parsing contiguous cells without labels are considered as part of the column.<br />
	 * In this implementation columns are composed of three TD.
	 * @see org.openxava.web.layout.ILayoutPainter#beginColumn(org.openxava.web.layout.LayoutElement)
	 */
	public void beginColumn(ILayoutColumnBeginElement element) {
		int count = getRow().getRowCurrentColumnsCount() + 1;
		getRow().setRowCurrentColumnsCount(count);
		setColumn(element);
		firstCellPainted = false; // to indicate to the cell renderer that the TD pair is about to start.
		smallLabelPainted = false;
	}

	/**
	 * In this painter implementation the column does end the last TD. So the cell
	 * implementation must start the first TD but NOT close the last one
	 * @see org.openxava.web.layout.ILayoutPainter#endColumn(org.openxava.web.layout.LayoutElement)
	 */
	public void endColumn(ILayoutColumnEndElement element) {
		closeSmallLabel();
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		unsetColumn();
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#beginProperty(org.openxava.web.layout.LayoutElement)
	 */
	public void beginProperty(ILayoutPropertyBeginElement element) {
		if (element.isDisplayAsDescriptionsList() 
				|| element.getMetaReference() != null) {
			beginReferenceData(element);
		} else {
			if (element.isMetaViewAction()) {
				beginMetaViewActionData(element);
			} else {
				beginPropertyData(element);
			}
		}
	}
	
	/**
	 * Display element as description list.
	 * @param element Element to be displayed.
	 */
	private void beginReferenceData(ILayoutPropertyBeginElement element) {
		initiatePropertyElement(element);
		beginPropertyLabelNormal(element);
		processPropertyElement(element);
		ModuleContext context = (ModuleContext) getPageContext().getSession().getAttribute("context");
		View view = (View)context.get(getRequest(), element.getViewObject());
		try {
			MetaReference metaReference = view.getMetaReference(element.getReferenceForDescriptionsList());
			String referenceKey = Ids.decorate(getRequest(), element.getPropertyKey());
			getRequest().setAttribute(referenceKey, metaReference);
			String editorURL = "reference.jsp"
					+ "?referenceKey=" + referenceKey 
					+ "&onlyEditor=true"
					+ "&frame=false"
					+ "&composite=false"
					+ "&descriptionsList=" + element.isDisplayAsDescriptionsList()
					+ "&viewObject=" + view.getViewObject(); 
			
			beginPropertyLabelSmall(element);
			if (!smallLabelPainted) {
				displayPropertyIcons(element);
				displayPropertyErrorIcon(element);
			} else {
				displayPropertyNoIcon(element);
			}

			
			includeJspPage(editorURL);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finalizePropertyElement(element, false);
	}
	
	/**
	 * Paints the input controls.
	 * @param element Element to be painted.
	 */
	protected void beginMetaViewActionData(ILayoutPropertyBeginElement element) {
		initiatePropertyElement(element);
		// No Label here.
		processPropertyElement(element);
		getRequest().setAttribute(element.getPropertyKey(), element.getMetaMember());
		MetaViewAction metaViewAction = (MetaViewAction) element.getMetaProperty();
		attributes.clear();
		attributes.put("id", Ids.decorate(getRequest(), "")
				+ ("editor_" 
				+ element.getPropertyPrefix()
				+ (!Is.emptyString(element.getPropertyPrefix()) ? "__" : "")
				+ "__ACTION__" + metaViewAction.getAction()).replaceAll("\\.", "_"));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));

		
		String editorURL = "editors/actionEditor.jsp"
				+ "?propertyKey=" + element.getPropertyKey() 
				+ "&editable=" + element.isEditable()
				+ "&viewObject=" + element.getViewObject(); 
		includeJspPage(editorURL);
		
		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
		finalizePropertyElement(element, false);
	}
	
	/**
	 * Paints the input controls.
	 * @param element Element to be painted.
	 */
	protected void beginPropertyData(ILayoutPropertyBeginElement element) {
		initiatePropertyElement(element);
		beginPropertyLabelNormal(element);
		processPropertyElement(element);
		beginPropertyLabelSmall(element);

		if (!smallLabelPainted) {
			displayPropertyIcons(element);
			displayPropertyErrorIcon(element);
		} else {
			displayPropertyNoIcon(element);
		}

		
		attributes.put(ATTR_ID, Ids.decorate(getRequest(), "editor_"
				+ element.getPropertyPrefix() + element.getName()));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
		
		getRequest().setAttribute(element.getPropertyKey(), element.getMetaMember());
		EditorTag editorTag = new EditorTag();
		editorTag.setProperty(element.getName());
		editorTag.setEditable(element.isEditable());
		editorTag.setViewObject(element.getView().getViewObject());
		editorTag.setPropertyPrefix(element.getPropertyPrefix());
		if (element.isEditable()) {
			if (element.getMetaProperty().isKey()) {
				editorTag.setEditable(element.getView().isKeyEditable());
			}
			if (element.isLastSearchKey() && element.isSearch()) {
				editorTag.setEditable(element.isEditable());
			}
		}
		editorTag.setPageContext(getPageContext());
		editorTag.setThrowPropertyChanged(element.isThrowPropertyChanged());

		try {
			editorTag.doStartTag();
		} catch (JspException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));

		beginPropertyDataAddPropertyActions(element);
		
		finalizePropertyElement(element, false);
	}
	
	/**
	 * Initiates a property element.
	 * @param element Current property element.
	 */
	private void initiatePropertyElement(ILayoutPropertyBeginElement element) {
		Integer width =  getMaxColumnsOnView() > 0 ? 50 / getMaxColumnsOnView() : 0;
		width = 0;
		smallLabelPainted = isSmallLabel(element);
		if (!firstCellPainted) {
			attributes.clear();
			attributes.put(ATTR_CLASS, getStyle().getLabel() + " " + getStyle().getLayoutLabelCell());
			StringBuffer style = new StringBuffer("");
			if (width > 0) {
				style.append("width:" + width.toString() + "%;");
			}
			style.append("white-space:nowrap;");
			attributes.put(ATTR_STYLE, style.toString());
			attributes.put("valign", "center");
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
			// If it is a small label and it is the first, the tag must be inmediately closed.
			if (smallLabelPainted) {
				style.append("padding-left:0px;");
				attributes.put(ATTR_STYLE, style.toString());
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
			}
		}
		if (smallLabelPainted) {
			attributes.clear();
			attributes.put(ATTR_STYLE, "display:inline-table; vertical-align:middle");
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
		}
	}
	
	/**
	 * Process a property element.
	 * @param element Element.
	 */
	private void processPropertyElement(ILayoutPropertyBeginElement element) {
		// Data. There is no end TD tag this one is closed by the end column method.
		if (!firstCellPainted && !smallLabelPainted) {
			closeFirstPropertyElement(element);
		}
	}
	
	/**
	 * Ends wrapping a property element.
	 * @param element Current property element.
	 */
	private void finalizePropertyElement(ILayoutPropertyBeginElement element, boolean closeFirst) {
		if (smallLabelPainted) {
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		}
		if (!firstCellPainted) {
			closeSmallLabel();
			if (closeFirst) {
				closeFirstPropertyElement(element);
			}
		}
		// Mark first cell painted
		firstCellPainted = true;
	}
	
	/**
	 * Closes just the first property element.
	 * @param element Element to close.
	 */
	private void closeFirstPropertyElement(ILayoutPropertyBeginElement element) {
		Integer width =  getMaxColumnsOnView() > 0 ? 50 / getMaxColumnsOnView() : 0;
		width = 0;

		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));

		attributes.clear();
		attributes.put(ATTR_CLASS, getStyle().getLayoutDataCell());
		StringBuffer style = new StringBuffer("");
		if (width > 0) {
			style.append("width:" + width.toString() + "%;");
		}
		style.append("white-space:nowrap;");
		if (style.length() > 0) {
			attributes.put(ATTR_STYLE, style.toString());
		}
		attributes.put("valign", "center");
		startTd();
	}
	
	/**
	 * Closes any small label.
	 */
	private void closeSmallLabel() {
		if (smallLabelPainted) {
			smallLabelPainted = false;
		}
	}
	
	/**
	 * Paints the cell label.
	 * @param element Representing cell element.
	 */
	protected void beginPropertyLabelNormal(ILayoutPropertyBeginElement element) {
		if (!smallLabelPainted) {
			beginPropertyLabel(element, element.getLabel());
		}
	}
	
	/**
	 * Display the given label. It only prints it if the label is printable.
	 * @param element Element containing the property attributes.
	 */
	protected void beginPropertyLabelSmall(ILayoutPropertyBeginElement element) {
		if (smallLabelPainted) {
			displayPropertyNoIcon(element);
			
			beginPropertyLabel(element, element.getLabel());
			
			displayPropertyIcons(element);
			displayPropertyErrorIcon(element);
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
		}
	}
	
	/**
	 * Display the given label. It only prints it if the label is printable.
	 * @param element Element containing the property attributes.
	 * @param sentLabel Label to print, might be a blank or null value.
	 */
	protected void beginPropertyLabel(ILayoutPropertyBeginElement element, String sentLabel) {
		if (isLabelShown(element)) {
			if (!smallLabelPainted) {
				// Left spacer
				beginPropertySpacer(element);
			}
			attributes.clear();
			if (!Is.emptyString(sentLabel)) {
				StringBuffer classAttribute = new StringBuffer("");
				if (element.getMetaProperty() == null ||
						element.getLabelFormat() != LabelFormatType.SMALL.ordinal()) {
					classAttribute.append(getStyle().getLabel()) 
						.append(' ');
				} else if (smallLabelPainted) {
					classAttribute.append(getStyle().getSmallLabel())
						.append(' ');
				}
				classAttribute.append(getStyle().getLayoutLabelCell())
					.append(' ');
				
				setId(element, "label");

				if (!Is.emptyString(element.getLabelStyle())) {
					classAttribute.append(element.getLabelStyle())
						.append(' ');
				}
				attributes.put(ATTR_CLASS, classAttribute.toString());
				attributes.put(ATTR_STYLE, "vertical-align:middle");
				write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
				String label = element.getLabelFormat() != LabelFormatType.NO_LABEL.ordinal() &&
						sentLabel != null ? sentLabel + LayoutJspKeys.CHAR_SPACE : LayoutJspKeys.CHAR_SPACE;
				label = label.replaceAll(" ", LayoutJspKeys.CHAR_SPACE);
				write(label);
				write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
			}
		}

	}
	
	/**
	 * Checks if the label is going to be shown or not.
	 * @param element Element containing the label.
	 * @return True if the label can be shown false otherwise.
	 */
	private boolean isLabelShown(ILayoutPropertyBeginElement element) {
		boolean returnValue = false;
		if (getContainer().getShowColumnLabel(getColumn().getColumnIndex())) {
			if (element.getLabelFormat() != LabelFormatType.NO_LABEL.ordinal()) {
				if (!element.isMetaViewAction()) {
					returnValue = true;
				}
			}
		}
		return returnValue;
	}
	
	/**
	 * Calculates if the label is small.
	 * @param element Current element containing the label.
	 * @return True if the element has a small label.
	 */
	private boolean isSmallLabel(ILayoutPropertyBeginElement element) {
		boolean returnValue = false;
		if (isLabelShown(element)) {
			if (element.getLabelFormat() == LabelFormatType.SMALL.ordinal()) {
				returnValue = true;
			}
		}
		return returnValue;
	}
	
	/**
	 * Adds the list of property Actions.
	 * @param element Element containing the actions.
	 */
	private void beginPropertyDataAddPropertyActions(ILayoutPropertyBeginElement element) {
		boolean isSearch = false;
		boolean isCreateNew = false;
		boolean isModify = false;
		try {
			isSearch = element.getView().isSearch();
		} catch (Exception e) {
			log.trace(e.getMessage());
		}
		try {
			isCreateNew = element.getView().isCreateNew();
		} catch (Exception e) {
			log.trace(e.getMessage());
		}
		try {
			isModify = element.getView().isModify();
		} catch (Exception e) {
			log.trace(e.getMessage());
		}
		beginPropertyDataAddActions(element, isSearch, isCreateNew, isModify);
	}
	
	/**
	 * Displays the no icon (actually blank) for the property.
	 * @param element Property element.
	 */
	private void displayPropertyNoIcon(ILayoutPropertyBeginElement element) {
		String img = "property_no_icon.gif";
		attributes.clear();
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
		attributes.clear();
		
		setId(element, "icon_no");
		
		attributes.put(ATTR_SRC, getRequest().getContextPath() + "/xava/images/" + img);
		write(LayoutJspUtils.INSTANCE.startTag(TAG_IMG, attributes));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_IMG));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));

	}
	
	/**
	 * Displays the icons associated with the property.
	 * @param element Element to be assess.
	 */
	private void displayPropertyIcons(ILayoutPropertyBeginElement element) {
		String img = "property_no_icon.gif";
		String prefix = "icon_no";
		
		if (!element.isDisplayAsDescriptionsList()) {
			if (element.isKey()) {
				img = "key.gif";
				prefix = "icon_key";
			} else if (element.isRequired()) {
				if (element.isEditable()) { // No need to mark it as required, since the user can not change it anyway
					img = "required.gif";
					prefix = "icon_required";
				}
			}
		} else if (element.isRequired()) {
			img = "required.gif";
			prefix = "icon_required";
		}
		if (!Is.emptyString(img)) {
			write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN));
				attributes.clear();
				
				setId(element, prefix);
				
				attributes.put(ATTR_SRC, getRequest().getContextPath() + "/xava/images/" + img);
				write(LayoutJspUtils.INSTANCE.startTag(TAG_IMG, attributes));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_IMG));
			write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
		}
	}
	
	private void displayPropertyErrorIcon(ILayoutPropertyBeginElement element) {
		attributes.clear();
		attributes.put(ATTR_ID, Ids.decorate(getRequest(), "error_image_" + element.getQualifiedName()));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
			if (getErrors().memberHas(element.getMetaMember())) {
				attributes.clear();
				if (!Is.emptyString(getStyle().getErrorImage())) {
					attributes.put(ATTR_CLASS, getStyle().getErrorImage());
				}
				attributes.put(ATTR_SRC, getRequest().getContextPath() + "/xava/images/error.gif");
				write(LayoutJspUtils.INSTANCE.startTag(TAG_IMG, attributes));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_IMG));
			}
		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
	}
 	
	private void setId(ILayoutPropertyBeginElement element, String prefix) {
		if (!element.isDisplayAsDescriptionsList()) {
			attributes.put(ATTR_ID, Ids.decorate(getRequest(), prefix + "_" + element.getPropertyPrefix() 
					+ element.getName()));
		} else {
			attributes.put(ATTR_ID, Ids.decorate(getRequest(), prefix + "_" + element.getPropertyPrefix() 
					+ element.getReferenceForDescriptionsList()));
		}

	}
	
	/**
	 * Starts a TD properly (with column span if needed).
	 */
	private void startTd() {
		if (getRow().getMaxRowColumnsCount() == getRow().getRowCurrentColumnsCount() &&
				!blockStarted) { // last column
			Integer columnSpan = getMaxColumnsOnView() - getRow().getRowCurrentColumnsCount();
			if (columnSpan > 0) {
				columnSpan = calculateTdSpan(columnSpan);
				columnSpan = columnSpan + 1;
				getRow().setRowCurrentColumnsCount(getMaxColumnsOnView()); // No td to add.
			}
		}
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
	}

	/**
	 * Paints the cell left spacer.
	 * @param element Representing cell element.
	 */
	protected void beginPropertySpacer(ILayoutPropertyBeginElement element) {
		attributes.clear();
		attributes.put(ATTR_STYLE, "width:" + getStyle().getPropertyLeftMargin() + "px");
		attributes.put(ATTR_SRC, getRequest().getContextPath() + "/xava/images/spacer.gif");
		write(LayoutJspUtils.INSTANCE.startTag(TAG_IMG, attributes));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_IMG));
	}
	


	@SuppressWarnings("rawtypes")
	/**
	 * Paints the action for editable properties according to the state of search, createNew and modify.
	 * @param element Current element.
	 * @param isSearch If true a search action is rendered.
	 * @param isCreateNew If true a create new object action is rendered.
	 * @param isModify If true a modify object is rendered.
	 */
	private void beginPropertyDataAddActions(ILayoutPropertyBeginElement element, 
			boolean isSearch, boolean isCreateNew, boolean isModify) {
		String propertyPrefix = element.getPropertyPrefix() == null ? "" : element.getPropertyPrefix();
		attributes.clear();
		attributes.put("id", Ids.decorate(getRequest(), "property_actions_" + propertyPrefix + element.getName()));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));

		ActionTag actionTag;
		try {
			if (element.hasActions()) {
				if (element.isLastSearchKey() && element.isEditable()) {
					if (isSearch) {
						actionTag = new ActionTag();
						actionTag.setAction(element.getSearchAction());
						actionTag.setArgv("keyProperty="+Ids.undecorate(element.getPropertyKey()));
						actionTag.setPageContext(getPageContext());
						actionTag.doStartTag();
					}
					if (isCreateNew) {
						actionTag = new ActionTag();
						actionTag.setAction("Reference.createNew");
						actionTag.setArgv("model=" + 
							element.getMetaMember().getMetaModel().getName() + ",keyProperty="+Ids.undecorate(element.getPropertyKey()));
						actionTag.setPageContext(getPageContext());
						actionTag.doStartTag();
					}
					if (isModify) {
						actionTag = new ActionTag();
						actionTag.setAction("Reference.modify");
						actionTag.setArgv("model=" + 
								element.getMetaMember().getMetaModel().getName() + ",keyProperty="+Ids.undecorate(element.getPropertyKey()));
						actionTag.setPageContext(getPageContext());
						actionTag.doStartTag();
					}
				}
			}
		} catch (JspException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		try {
			if (element.isEditable() && element.getActionsNameForReference() != null
					&& element.getActionsNameForReference().size() > 0) {
				Iterator it = element.getActionsNameForReference().iterator();
				while(it.hasNext()) {
					String action = (String) it.next();
					actionTag = new ActionTag();
					actionTag.setAction(action);
					actionTag.setPageContext(getPageContext());
					actionTag.doStartTag();
					actionTag.doEndTag();
				}
			}
			if (element.getActionsNameForProperty() != null 
					&& element.getActionsNameForProperty().size() > 0) {
				Iterator it = element.getActionsNameForProperty().iterator();
				while(it.hasNext()) {
					String action = (String) it.next();
					actionTag = new ActionTag();
					actionTag.setAction(action);
					actionTag.setArgv("xava.keyProperty=" + Ids.undecorate(element.getPropertyKey()));
					actionTag.setPageContext(getPageContext());
					actionTag.doStartTag();
					actionTag.doEndTag();
				}
			}
		} catch (JspException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
		
	}

	
	/**
	 * In this implementation nothing is done at cell end.
	 * @see org.openxava.web.layout.ILayoutPainter#endProperty(org.openxava.web.layout.LayoutElement)
	 */
	public void endProperty(ILayoutPropertyEndElement element) {

	}

	/**
	 * Besides the frame handling, it lets the collection.jsp to take care of the collection rendering.
	 * @see org.openxava.web.layout.ILayoutPainter#beginCollection(org.openxava.web.layout.LayoutElement)
	 */
	public void beginCollection(ILayoutCollectionBeginElement element) {
		String propertyPrefix = element.getView().getPropertyPrefix();
		String collectionPrefix = propertyPrefix == null ? element.getMetaCollection().getName() + "." :
				propertyPrefix + element.getMetaCollection().getName() + ".";
		String collectionId = Ids.decorate(getRequest(), "collection_" + collectionPrefix);
		attributes.clear();
		int percent = 100; 
		if (getRow().getMaxFramesCount() > 1) {
			percent = 100 / getRow().getMaxFramesCount(); 
			StringBuffer style = new StringBuffer("");
			if (element.isFirst()) {
				style.append("float:left; margin:0px;");
			} else {
				style.append("float:left; margin-left:" + getStyle().getPropertyLeftMargin() + "px;");
			}
			style.append("overflow: auto; display: block; width: ")
					.append(percent - 4)
					.append('%');
			attributes.put(ATTR_CLASS, getStyle().getFrame());
			attributes.put(ATTR_STYLE, style.toString());
		} else {
			attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
		}
		startTd();
		if (element.hasFrame()) {
			write(getStyle().getCollectionFrameHeaderStartDecoration(percent)); 
			write(getStyle().getFrameTitleStartDecoration());
			write(element.getLabel());
			String frameId = Ids.decorate(getRequest(), "frame_" + element.getView().getPropertyPrefix() + element.getMetaCollection().getName());
			String collectionHeaderId = frameId + "header";
			attributes.clear();
			attributes.put("id", collectionHeaderId);
			write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
			includeJspPage("collectionFrameHeader.jsp" 
				+ "?collectionName=" + element.getMetaCollection().getName()
				+ "&viewObject=" + element.getView().getViewObject()
				+ "&propertyPrefix=" + propertyPrefix);
			write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
			write(getStyle().getFrameTitleEndDecoration());
			write(getStyle().getFrameActionsStartDecoration());
			String frameActionsURL = "frameActions.jsp?frameId=" + frameId +
					"&closed=" + element.getView().isFrameClosed(frameId);
			includeJspPage(frameActionsURL);
			write(getStyle().getFrameActionsEndDecoration());
			write(getStyle().getFrameHeaderEndDecoration());
			write(getStyle().getFrameContentStartDecoration(frameId + "content", element.getView().isFrameClosed(frameId)));
		}
		attributes.clear();
		attributes.put("id", collectionId);
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutContent());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_DIV, attributes));
			includeJspPage("collection.jsp"
					+ "?collectionName=" + element.getMetaCollection().getName() 
					+ "&viewObject=" + element.getView().getViewObject()
					+ "&propertyPrefix=" + propertyPrefix);
		write(LayoutJspUtils.INSTANCE.endTag(TAG_DIV));
		if (element.hasFrame()) {
			write(getStyle().getFrameContentEndDecoration());
		}
	}

	/**
	 * Actually all code is performed in the startCollection method.
	 * However for future implementations this might be useful for adding
	 * features to the collection that might be painted at end of the collection
	 * renderization.
	 * @see org.openxava.web.layout.ILayoutPainter#endCollection(org.openxava.web.layout.LayoutElement)
	 */
	public void endCollection(ILayoutCollectionEndElement element) {

	}
	
	/**
	 * Each section behave as a marker. This is called upon section change or page reload. 
	 * @see org.openxava.web.layout.ILayoutPainter#beginSections(org.openxava.web.layout.LayoutElement)
	 */
	public void beginSections(ILayoutSectionsBeginElement element) {
		View view = element.getView().hasSections() ? element.getView() : getView();
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		attributes.clear();
		attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
		attributes.put(ATTR_CELL_SPACING, "0");
		attributes.put(ATTR_BORDER, "0");
		attributes.put(ATTR_CELL_PADDING, "0");
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
		
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
		attributes.clear();
		attributes.put(ATTR_COLSPAN, Integer.toString(calculateTdSpan(getMaxColumnsOnView())));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
		attributes.clear();
		attributes.put(ATTR_ID, Ids.decorate(getRequest(), "sections_" + view.getViewObject()));
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutContent());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_DIV, attributes));
	}
	
	@SuppressWarnings("rawtypes")
	public void beginSectionsRender(ILayoutSectionsRenderBeginElement element) {
		View view = element.getView().hasSections() ? element.getView() : getView();
		View sectionView = view.getSectionView(view.getActiveSection());

		Collection sections = view.getSections();
		int activeSection = view.getActiveSection();
		if (view.getViewObject() == null) {
			view.setViewObject("xava_view");
		}
		if (sectionView != null && !Is.emptyString(sectionView.getViewObject())) {
			ModuleContext context = (ModuleContext) getRequest().getSession().getAttribute("context");
			context.put(getRequest(), sectionView.getViewObject(), sectionView);
		}
		attributes.clear();
		attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
		attributes.put(ATTR_CELL_SPACING, "0");
		attributes.put(ATTR_BORDER, "0");
		attributes.put(ATTR_CELL_PADDING, "0");
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
					attributes.clear();
					attributes.put(ATTR_CLASS, getStyle().getSection());
					write(LayoutJspUtils.INSTANCE.startTag(TAG_DIV, attributes));
						attributes.clear();
						attributes.put(ATTR_LIST, getStyle().getSectionTableAttributes());
						attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
						attributes.put(ATTR_CELL_SPACING, "0");
						attributes.put(ATTR_BORDER, "0");
						attributes.put(ATTR_CELL_PADDING, "0");
						write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
							write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
								
								write(getStyle().getSectionBarStartDecoration());
								// Loop to paint section(s)
								Iterator itSections = sections.iterator();
								int i = 0;
								while(itSections.hasNext()) {
									MetaView section = (MetaView) itSections.next();
									if (activeSection == i) {
										write(getStyle().getActiveSectionTabStartDecoration(i == 0, !itSections.hasNext()));
										write(section.getLabel(getRequest()));
										write(getStyle().getActiveSectionTabEndDecoration());
									} else {
										try {
											write(getStyle().getSectionTabStartDecoration(i == 0, !itSections.hasNext()));
											String viewObjectArgv = "xava_view".equals(view.getViewObject())
													? "" : ",viewObject=" + view.getViewObject();
											LinkTag linkTag = new LinkTag();
											linkTag.setAction("Sections.change");
											linkTag.setArgv("activeSection=" + i + viewObjectArgv);
											linkTag.setCssClass(getStyle().getSectionLink());
											linkTag.setCssStyle(getStyle().getSectionLinkStyle());
											linkTag.setPageContext(getPageContext());
											linkTag.doStartTag();
											write(section.getLabel(getRequest()));
											linkTag.doAfterBody();
											linkTag.doEndTag();
											write(getStyle().getSectionTabEndDecoration());
										} catch (JspException e) {
											log.error(e.getMessage(), e);
											throw new RuntimeException(e);
										}
									}
									i++;
								}
								write(getStyle().getSectionBarEndDecoration());
							write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
						write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
					write(LayoutJspUtils.INSTANCE.endTag(TAG_DIV));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
				attributes.clear();
				attributes.put(ATTR_CLASS, getStyle().getActiveSection());
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
				attributes.clear();
				attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
				attributes.put(ATTR_CELL_SPACING, "0");
				attributes.put(ATTR_BORDER, "0");
				attributes.put(ATTR_CELL_PADDING, "0");
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
					write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
					includeJspPage("detail.jsp"
									+ "?viewObject=" + sectionView.getViewObject()
									+ "&propertyPrefix=" + sectionView.getPropertyPrefix()
									+ "&representsSection=true");
	}

	public void endSectionsRender(ILayoutSectionsRenderEndElement element) {
		
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#endSections(org.openxava.web.layout.LayoutElement)
	 */
	public void endSections(ILayoutSectionsEndElement element) {
		write(LayoutJspUtils.INSTANCE.endTag(TAG_DIV));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
	}
	
	private int calculateTdSpan(int currentCount) {
		return currentCount * tdPerColumn;
	}
	
	/**
	 * @see org.openxava.web.layout.ILayoutPainter#defaultBeginSectionsRenderElement(org.openxava.web.layout.ILayoutSectionsBeginElement)
	 */
	public ILayoutSectionsRenderBeginElement defaultBeginSectionsRenderElement(View view) {
		return new DefaultLayoutSectionsRenderBeginElement(view, 0);
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#defaultEndSectionsRenderElement(org.openxava.web.layout.ILayoutSectionsBeginElement)
	 */
	public ILayoutSectionsRenderEndElement defaultEndSectionsRenderElement(View view) {
		return new DefaultLayoutSectionsRenderEndElement(view, 0);
	}

	/**
	 * @param view Current main view.
	 * @return True if the frames should occupy 100% of the view.
	 */
	protected boolean viewFullWidthFrames(View view) {
		return true;
	}
	
	/**
	 * @param view Current section view.
	 * @return True if the frames should occupy 100% of the view.
	 */
	protected boolean sectionFullWidthFrames(View view) {
		return true;
	}

}
