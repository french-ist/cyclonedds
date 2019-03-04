/**
 *                             Vortex Cafe
 *
 *    This software and documentation are Copyright 2010 to 2019 ADLINK
 *    Technology Limited, its affiliated companies and licensors. All rights
 *    reserved.
 *
 *    Licensed under the ADLINK Software License Agreement Rev 2.7 2nd October
 *    2014 (the "License"); you may not use this file except in compliance with
 *    the License.
 *    You may obtain a copy of the License at:
 *                        docs/LICENSE.html
 *
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.eclipse.cyclonedds.idl2j

import util.parsing.combinator.JavaTokenParsers
import java.io.{FileInputStream, File}
import io.BufferedSource

import scala.util.parsing.combinator.JavaTokenParsers
import java.io.{FileInputStream, File}
import scala.io.BufferedSource

case class KeyListDescriptor(typeName: String, keyList: String)

class KeyListParser extends JavaTokenParsers {


  private val moduleStack = new scala.collection.mutable.Stack[String]()
  private var keyDeclList = List[KeyListDescriptor]()

  def getKeyDeclList() = keyDeclList

  // Skip comments and white spaces
  protected override val whiteSpace = """(?>\s|//.*|(?m)/\*(?>\*(?!/)|[^*])*\*/)+""".r


  def root = rep(preprocessorDecl)~definitionList
  def definition: Parser[Any] = ifdefDecl | ifndefDecl | elseIfDecl | defineDecl | endifDecl | typeDecl | constDecl | module | pragmas

  def module = moduleBegin~definitionList~moduleEnd
  def moduleBegin = "module"~ident~"{" ^^ {case "module"~identifier~"{" => {
    moduleStack push identifier
  }}
  def moduleEnd = "}"~";" ^^ { x => {
    moduleStack pop()
  }}

  def definitionList = rep(definition)

  def xident = rep1(rep("_")~(ident | decimalNumber))
  def constDecl = nconstDecl | sconstDecl
  def nconstDecl = "const"~constType~ident~"="~wholeNumber~";"
  def sconstDecl = "const"~constType~ident~"="~constExpr~";"

  def typeDecl = typeDef | structType | unionType | enumType | forwardDecl

  def uscoredName = ident~rep(rep1("_")~ident)
  def scopedType = opt("::")~uscoredName~rep("::"~uscoredName)

  // typedef
  def typeDef = "typedef"~typeSpec~rep(ident)~opt("["~(decimalNumber | xident)~"]")~";"
  def typeSpec = simpleTypeSpec | constrTypeSpec
  def simpleTypeSpec = templateTypeSpec | scopedType | stringLiteral | literal
  def templateTypeSpec = sequenceType | templateStringType | templateWStringType
  def sequenceType = ("sequence"~"<"~rep(ident)~">"
        | "sequence"~"<"~scopedType~">"
        | "sequence"~"<"~rep(ident)~","~(decimalNumber | xident)~">"
        |  "sequence"~"<"~scopedType~","~(decimalNumber | xident)~">"
    )
  def constrTypeSpec = structType | unionType | enumType


  // struct
  def structType = "struct"~ident~"{"~memberList~"}"~";"
  def memberList = rep(member)
  def member = ( templateTypeSpec~xident | builtinTypes~xident | scopedType~xident )~rep("["~(decimalNumber | xident)~"]")~";"

  // enum
  def enumType = "enum"~ident~"{"~repsep(ident, ",")~"}"~";"

  // union
  def unionType = "union"~ident~"switch"~"("~constType~")"~"{"~switchBody~"}"~";"
  def switchBody = rep((rep("case"~constExpr~":") | "default"~":")~member)
  def constExpr = decimalNumber | stringLiteral | charLiteral | booleanLiteral | scopedName
  def charLiteral = "\'\\w\'".r
  def booleanLiteral = "TRUE" | "FALSE"

  def forwardDecl = ("struct"~ident | "union"~ident)~";"

  def preprocessorDecl = ifdefDecl | ifndefDecl | elseIfDecl | defineDecl | endifDecl | includeDecl | importDecl
  def includeDecl = guardedinclude | simpleIncludeDecl

  def guardedinclude = (ifdefDecl | ifndefDecl)~rep(simpleIncludeDecl)~opt(elseIfDecl~rep(simpleIncludeDecl))~endifDecl


  def includePath = repsep(ident, "/")~"."~ident
  def simpleIncludeDecl = "#include"~("\""~includePath~"\"" | "<"~includePath~">")

  def ifdefDecl = "#ifdef"~ident
  def defineDecl = "#define"~ident
  def ifndefDecl = "#ifndef"~ident
  def endifDecl = "#endif"
  def elseIfDecl = "#else"
  def importDecl = "import"~(stringLiteral | scopedName)~";"
  def scopedName = repsep(ident, "::")

  def pragmas = keyListDecl | pragmaPrefixDecl
  def pragmaPrefixDecl = "#pragma"~"prefix"~stringLiteral

  def keyListDecl = "#pragma"~"keylist"~ident~keyList ^^ {case "#pragma"~"keylist"~ident~keyList => {
    val struct = ident
    val fqName = if (moduleStack.length == 0) struct else {
      val rs = moduleStack.reverse
      (rs.pop /: rs) (_ + "." + _) + "." + struct
    }

    val csklist = keyList match {
      case List() => ""
      case x::xs => (x /: xs) (_ + ", " + _)
    }

    keyDeclList = keyDeclList ::: List(KeyListDescriptor(fqName, csklist ))
    keyDeclList
  } }

  def keyList = rep(keyMember)

  def keyMember: Parser[String] = scopedIdlIdent | idlIdent

  def scopedIdlIdent = idlIdent~"."~repsep(idlIdent, ".") ^^ { r => r._2 match {
    case List() => r._1._1
    case x::xs => (r._1._1 /: r._2)(_ + "." + _)
  }}
  def reserved = "struct " | "union " | "enum " | "typedef " | basicType~" " | "unsigned " | "#pragma " | "const "

  def idlIdent = not(reserved) ~> ident ^^ { r => r}


  def constType = basicType | scopedName

  def basicType =
    intType | charType | wCharType | boolType | floatType | stringType | wstringType | octetType

  def charType = "char"
  def wCharType = "wchar"
  def boolType = "boolean"
  def floatType = "float" | "double" | "long"<~"double"
  def templateStringType = "string"~"<"~(decimalNumber | xident)~">"
  def stringType =  templateStringType | "string"
  def templateWStringType = "wstring"~"<"~(decimalNumber | xident)~">"
  def wstringType = templateWStringType  | "wstring"

  def intType = uIntType | sIntType
  def sIntType =  "short" | "int" | "long"~"long" | "long"
  def uIntType = "unsigned"~sIntType
  def octetType = "octet"

  def builtinTypes = charType | wCharType | boolType | floatType | sIntType | intType | octetType | stringType | wstringType

  // This might need change
  def constExp = stringLiteral

  // This needs extension if we want to parse literals
  def literal = ident
}

object KeyListParser {
  def parse(fileName: String): List[KeyListDescriptor] = {
    val ifile = new File(fileName)
    val istream = new FileInputStream(ifile)
    val src = new BufferedSource(istream)
    val idl = ("" /: src.getLines())(_ + "\n" + _)

    val parser = new KeyListParser()
    val result = parser.parseAll(parser.root, idl)
    if (result.successful)  parser.getKeyDeclList() else {
      throw new Exception("ERROR parsing "+ifile.getAbsolutePath()+" :\n"+result)
    }
  }

  import scala.collection.JavaConversions._
  def jparse(fileName: String) = asJavaCollection(parse(fileName))

  def main(args: Array[String]) {
    if (args.length < 1) {
      println("USAGE sidlc <idl source>")
      sys.exit(1)
    }
    val kls = parse(args(0))
    println(kls)
  }
}
