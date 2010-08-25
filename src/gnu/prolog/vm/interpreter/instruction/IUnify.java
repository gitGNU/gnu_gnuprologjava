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
import gnu.prolog.vm.BacktrackInfo;
import gnu.prolog.vm.PrologException;
import gnu.prolog.vm.PrologCode.RC;
import gnu.prolog.vm.interpreter.ExecutionState;
import gnu.prolog.vm.interpreter.ExecutionState.EXRC;

/**
 * call instruction.
 */
public class IUnify extends Instruction
{
	/** a constructor */
	public IUnify()
	{}

	/** convert instruction to string */
	@Override
	public String toString()
	{
		return codePosition + ": unify";
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
		Term arg2 = state.popPushDown().dereference();
		Term arg1 = state.popPushDown().dereference();
		RC rc = state.interpreter.unify(arg1, arg2);
		EXRC exrc = EXRC.BACKTRACK;
		switch (rc)
		{
			case SUCCESS_LAST:
				exrc = ExecutionState.EXRC.NEXT; /* proceed to next instruction */
				// System.err.println("success last: "+gnu.prolog.io.TermWriter.toString(tag.getPredicateIndicator()));
				break;
			case FAIL:
				exrc = ExecutionState.EXRC.BACKTRACK; /* backtrack */
				// System.err.println("fail: "+gnu.prolog.io.TermWriter.toString(tag.getPredicateIndicator()));
				break;
		}
		return exrc;
	}
}
