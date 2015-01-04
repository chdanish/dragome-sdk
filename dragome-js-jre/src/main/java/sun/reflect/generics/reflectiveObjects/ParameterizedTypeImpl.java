/*
 * Copyright (c) 2011-2014 Fernando Petrola
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sun.reflect.generics.reflectiveObjects;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeImpl implements ParameterizedType
{
	private Type[] actualTypeArguments;
	private Class<?> rawType;
	private Type ownerType;
	private String genericSignature;

	public ParameterizedTypeImpl(String genericSignature)
	{
		this.genericSignature= genericSignature;
	}

	public Type[] getActualTypeArguments()
	{
		Class<?> type;
		try
		{
			type= Class.forName(genericSignature);
			return new Type[] { type };
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	public Type getRawType()
	{
		return null;
	}

	public Type getOwnerType()
	{
		return null;
	}
}
