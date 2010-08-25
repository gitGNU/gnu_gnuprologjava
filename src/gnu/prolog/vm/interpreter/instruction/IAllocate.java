/* GNU Prolog for Java
 * Copyright (C) 1997-1999  Constantine Plotnikov
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA. The text of license can be also found
 * at http://www.gnu.org/copyleft/lgpl.html
 */
package gnu.prolog.vm.interpreter.instruction;

import gnu.prolog.term.Term;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.vm.BacktrackInfo;
import gnu.prolog.vm.PrologException;
import gnu.prolog.vm.interpreter.ExecutionState;
import gnu.prolog.vm.interpreter.ExecutionState.EXRC;

/** allocate environment instruction */
public class IAllocate extends Instruction
{
	/** size of environment */
	public int environmentSize;
	/**
	 * reserved fields, fields are reserved for saved choice points and arguments,
	 * reserved fields are included in environment size but they are not
	 * initilized with unbound variables
	 */
	public int reserved;

	/** convert instruction to string */
	@Override
	public String toString()
	{
		return codePosition + ": allocate " + environmentSize + ", " + reserved;
	}

	public IAllocate(int environmentSize, int reserved)
	{
		this.environmentSize = environmentSize;
		this.reserved = reserved;
	}

	/**
	 * execute call instruction within specified sate
	 * 
	 * @param state
	 *          state within which instruction will be executed
	 * @return instruction to caller how to execute next instruction
	 * @throws PrologException
	 *           if code is throwing prolog exception
	 */
	@Override
	public EXRC execute(ExecutionState state, BacktrackInfo bi) throws PrologException
	{
		Term env[] = new Term[environmentSize];
		for (int i = reserved; i < environmentSize; i++)
		{
			env[i] = new VariableTerm();
		}
		state.environment = env;
		return ExecutionState.EXRC.NEXT;
	}
}
