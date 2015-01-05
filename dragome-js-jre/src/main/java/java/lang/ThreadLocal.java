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
package java.lang;

public class ThreadLocal<T>
{
	private T value;

	public ThreadLocal()
	{
		value= initialValue();
	}

	public void remove()
	{
		value= null;
	}

	public T get()
	{
		if (value == null)
			value= initialValue();

		return value;
	}

	protected T initialValue()
	{
		return value;
	}

	public void set(T value)
	{
		this.value= value;
	}

	static class ThreadLocalMap
	{
		public <S> ThreadLocalMap(InheritableThreadLocal<S> inheritableThreadLocal, S firstValue)
		{
		}

	}
}
