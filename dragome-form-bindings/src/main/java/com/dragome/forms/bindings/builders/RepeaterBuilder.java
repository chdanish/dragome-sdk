/*******************************************************************************
 * Copyright (c) 2011-2014 Fernando Petrola
 * 
 *  This file is part of Dragome SDK.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.dragome.forms.bindings.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dragome.model.VisualPanelImpl;
import com.dragome.model.interfaces.ValueChangeEvent;
import com.dragome.model.interfaces.ValueChangeHandler;
import com.dragome.model.interfaces.VisualComponent;
import com.dragome.model.interfaces.VisualPanel;
import com.dragome.templates.SimpleItemProcessor;
import com.dragome.templates.TemplateRepeater;
import com.dragome.templates.interfaces.Template;

public class RepeaterBuilder<T>
{
	private ValueModelDelegator<List<T>> valueModelDelegator;
	private Template mainTemplate;
	private Template template;
	private Supplier<Tester<T>> filter;
	private VisualPanel panel;
	private TemplateComponentBindingBuilder<? extends VisualComponent> templateComponentBindingBuilder;
	private Getter<T, Comparable<?>> orderByGetter;
	private Supplier<Order> order;

	public RepeaterBuilder(ValueModelDelegator<List<T>> valueModelDelegator, Template template, VisualPanel panel, TemplateComponentBindingBuilder<VisualPanel> templateComponentBindingBuilder)
	{
		this.valueModelDelegator= valueModelDelegator;
		this.template= template;
		this.panel= panel;
		this.templateComponentBindingBuilder= templateComponentBindingBuilder;
	}

	public RepeaterBuilder<T> repeat(final ItemRepeater<T> itemRepeater)
	{
		final List<T> list= new ArrayList<T>(valueModelDelegator.getValue());

		final TemplateRepeater<T> templateRepeater= new TemplateRepeater<T>(list, template.getParent(), template.getName(), new SimpleItemProcessor<T>()
		{
			public void fillTemplate(T item, Template aTemplate)
			{
				VisualPanelImpl itemPanel= new VisualPanelImpl(aTemplate);
				panel.addChild(itemPanel);
				ComponentBuilder componentBuilder= new ComponentBuilder(itemPanel, templateComponentBindingBuilder);
				itemRepeater.process(item, componentBuilder);
				componentBuilder.build();
			}
		}, true);
		templateRepeater.repeatItems();

		valueModelDelegator.addValueChangeHandler(new ValueChangeHandler<List<T>>()
		{
			public void onValueChange(ValueChangeEvent<List<T>> event)
			{
				List<T> value= valueModelDelegator.getValue();
				List<T> collectedList= collectItems(value);

				list.clear();
				list.addAll(collectedList);

				if (orderByGetter != null)
					Collections.sort(list, new Comparator<T>()
					{
						public int compare(T o1, T o2)
						{
							if (o1 != null && o2 != null)
							{
								Comparable comparable= (Comparable) orderByGetter.get(o1);
								Comparable<?> another= orderByGetter.get(o2);
								if (comparable != null && another != null)
								{
									int compareTo= comparable.compareTo(another);
									return compareTo * (order.get() == Order.DESC ? -1 : 1);
								}
							}

							return 0;
						}
					});

				templateRepeater.clearAndRepeatItems();
			}

			private List<T> collectItems(List<T> value)
			{
				List<T> collectedList= new ArrayList<T>();
				for (T t : value)
					if (filter == null || filter.get().test(t))
						collectedList.add(t);

				// Java 8 Stream Implementation
				// List<T> collectedList= valueModelDelegator.getValue().stream().filter(filter.get(model)).collect(Collectors.toList());
				return collectedList;
			}
		});

		return this;
	}

	public RepeaterBuilder<T> filter(Supplier<Tester<T>> aFilter)
	{
		this.filter= aFilter;
		return this;
	}

	public RepeaterBuilder<T> orderBy(Getter<T, Comparable<?>> getter, Supplier<Order> order)
	{
		this.orderByGetter= getter;
		this.order= order;
		return this;
	}

	public TemplateComponentBindingBuilder<?> builder()
	{
		return templateComponentBindingBuilder;
	}

}
