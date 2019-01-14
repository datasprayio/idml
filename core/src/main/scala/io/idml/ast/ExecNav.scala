package io.idml.ast

import io.idml.datanodes.PObject
import io.idml.{PtolemyContext, PtolemyObject}

/** Nav expressions set the cursor to a starting value */
trait ExecNav extends Expression

/** The base of the expression is a literal value */
case class ExecNavLiteral(literal: Literal) extends ExecNav {
  def invoke(ctx: PtolemyContext) {
    literal.invoke(ctx)
  }
}

/** The base of the expression is a variable */
case object ExecNavVariable extends ExecNav {
  def invoke(ctx: PtolemyContext) {
    ctx.cursor = ctx.output
  }
}

/** The base of the expression is a relative path */
case object ExecNavRelative extends ExecNav {
  def invoke(ctx: PtolemyContext) {
    ctx.cursor = ctx.scope
  }
}

/** The base of the expression is an absolute path */
case object ExecNavAbsolute extends ExecNav {
  def invoke(ctx: PtolemyContext) {
    ctx.cursor = ctx.input
  }
}

case object ExecNavTemp extends ExecNav {
  def invoke(ctx: PtolemyContext): Unit = {
    ctx.cursor = ctx.state.getOrElseUpdate(Variable.stateKey, PObject()).asInstanceOf[PtolemyObject]
  }
}
