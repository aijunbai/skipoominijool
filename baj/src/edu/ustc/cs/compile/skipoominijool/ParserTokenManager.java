/* Generated By:JavaCC: Do not edit this line. ParserTokenManager.java */
package  edu.ustc.cs.compile.skipoominijool;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.LinkedList;
import org.eclipse.jdt.core.dom.*;
import edu.ustc.cs.compile.platform.interfaces.ParserInterface;
import edu.ustc.cs.compile.platform.interfaces.ParserException;
import edu.ustc.cs.compile.platform.interfaces.InterRepresent;
import edu.ustc.cs.compile.platform.util.ir.HIR;
import edu.ustc.cs.compile.platform.util.ASTView.core.*;
import edu.ustc.cs.compile.platform.util.ASTView.plugin.*;

public class ParserTokenManager implements ParserConstants
{
  public  java.io.PrintStream debugStream = System.out;
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0xe0ffff000L) != 0L)
         {
            jjmatchedKind = 36;
            return 13;
         }
         if ((active0 & 0x8000000000000080L) != 0L || (active1 & 0x10L) != 0L)
            return 21;
         return -1;
      case 1:
         if ((active0 & 0xe0ffdf000L) != 0L)
         {
            jjmatchedKind = 36;
            jjmatchedPos = 1;
            return 13;
         }
         if ((active0 & 0x20000L) != 0L)
            return 13;
         if ((active0 & 0x80L) != 0L)
            return 19;
         return -1;
      case 2:
         if ((active0 & 0xe0ffdb000L) != 0L)
         {
            jjmatchedKind = 36;
            jjmatchedPos = 2;
            return 13;
         }
         if ((active0 & 0x4000L) != 0L)
            return 13;
         return -1;
      case 3:
         if ((active0 & 0x205040000L) != 0L)
            return 13;
         if ((active0 & 0xc0af9b000L) != 0L)
         {
            jjmatchedKind = 36;
            jjmatchedPos = 3;
            return 13;
         }
         return -1;
      case 4:
         if ((active0 & 0x802519000L) != 0L)
         {
            jjmatchedKind = 36;
            jjmatchedPos = 4;
            return 13;
         }
         if ((active0 & 0x408a82000L) != 0L)
            return 13;
         return -1;
      case 5:
         if ((active0 & 0x109000L) != 0L)
         {
            jjmatchedKind = 36;
            jjmatchedPos = 5;
            return 13;
         }
         if ((active0 & 0x802410000L) != 0L)
            return 13;
         return -1;
      case 6:
         if ((active0 & 0x100000L) != 0L)
         {
            jjmatchedKind = 36;
            jjmatchedPos = 6;
            return 13;
         }
         if ((active0 & 0x9000L) != 0L)
            return 13;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0, long active1)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
}
private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         jjmatchedKind = 51;
         return jjMoveStringLiteralDfa1_0(0x80000000000000L, 0x0L);
      case 37:
         jjmatchedKind = 64;
         return jjMoveStringLiteralDfa1_0(0x0L, 0x20L);
      case 38:
         return jjMoveStringLiteralDfa1_0(0x200000000000000L, 0x0L);
      case 40:
         return jjStopAtPos(0, 39);
      case 41:
         return jjStopAtPos(0, 40);
      case 42:
         jjmatchedKind = 62;
         return jjMoveStringLiteralDfa1_0(0x0L, 0x8L);
      case 43:
         jjmatchedKind = 60;
         return jjMoveStringLiteralDfa1_0(0x400000000000000L, 0x2L);
      case 44:
         return jjStopAtPos(0, 46);
      case 45:
         jjmatchedKind = 61;
         return jjMoveStringLiteralDfa1_0(0x800000000000000L, 0x4L);
      case 46:
         return jjStopAtPos(0, 47);
      case 47:
         jjmatchedKind = 63;
         return jjMoveStringLiteralDfa1_0(0x80L, 0x10L);
      case 59:
         return jjStopAtPos(0, 45);
      case 60:
         jjmatchedKind = 49;
         return jjMoveStringLiteralDfa1_0(0x20000000000000L, 0x0L);
      case 61:
         jjmatchedKind = 48;
         return jjMoveStringLiteralDfa1_0(0x10000000000000L, 0x0L);
      case 62:
         jjmatchedKind = 50;
         return jjMoveStringLiteralDfa1_0(0x40000000000000L, 0x0L);
      case 80:
         return jjMoveStringLiteralDfa1_0(0x1000L, 0x0L);
      case 83:
         return jjMoveStringLiteralDfa1_0(0x10000L, 0x0L);
      case 91:
         return jjStopAtPos(0, 43);
      case 93:
         return jjStopAtPos(0, 44);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x208000L, 0x0L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x102000L, 0x0L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x40000L, 0x0L);
      case 102:
         return jjMoveStringLiteralDfa1_0(0x400800000L, 0x0L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x24000L, 0x0L);
      case 108:
         return jjMoveStringLiteralDfa1_0(0x800000000L, 0x0L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x8000000L, 0x0L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x6000000L, 0x0L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x400000L, 0x0L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x200000000L, 0x0L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x1000000L, 0x0L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x80000L, 0x0L);
      case 123:
         return jjStopAtPos(0, 41);
      case 124:
         return jjMoveStringLiteralDfa1_0(0x100000000000000L, 0x0L);
      case 125:
         return jjStopAtPos(0, 42);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private final int jjMoveStringLiteralDfa1_0(long active0, long active1)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0, active1);
      return 1;
   }
   switch(curChar)
   {
      case 38:
         if ((active0 & 0x200000000000000L) != 0L)
            return jjStopAtPos(1, 57);
         break;
      case 42:
         if ((active0 & 0x80L) != 0L)
            return jjStartNfaWithStates_0(1, 7, 19);
         break;
      case 43:
         if ((active0 & 0x400000000000000L) != 0L)
            return jjStopAtPos(1, 58);
         break;
      case 45:
         if ((active0 & 0x800000000000000L) != 0L)
            return jjStopAtPos(1, 59);
         break;
      case 61:
         if ((active0 & 0x10000000000000L) != 0L)
            return jjStopAtPos(1, 52);
         else if ((active0 & 0x20000000000000L) != 0L)
            return jjStopAtPos(1, 53);
         else if ((active0 & 0x40000000000000L) != 0L)
            return jjStopAtPos(1, 54);
         else if ((active0 & 0x80000000000000L) != 0L)
            return jjStopAtPos(1, 55);
         else if ((active1 & 0x2L) != 0L)
            return jjStopAtPos(1, 65);
         else if ((active1 & 0x4L) != 0L)
            return jjStopAtPos(1, 66);
         else if ((active1 & 0x8L) != 0L)
            return jjStopAtPos(1, 67);
         else if ((active1 & 0x10L) != 0L)
            return jjStopAtPos(1, 68);
         else if ((active1 & 0x20L) != 0L)
            return jjStopAtPos(1, 69);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x400000000L, active1, 0L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x806000000L, active1, 0L);
      case 102:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(1, 17, 13);
         break;
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x80000L, active1, 0L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000L, active1, 0L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x42000L, active1, 0L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000L, active1, 0L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x1108000L, active1, 0L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x208201000L, active1, 0L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x410000L, active1, 0L);
      case 124:
         if ((active0 & 0x100000000000000L) != 0L)
            return jjStopAtPos(1, 56);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0, active1);
}
private final int jjMoveStringLiteralDfa2_0(long old0, long active0, long old1, long active1)
{
   if (((active0 &= old0) | (active1 &= old1)) == 0L)
      return jjStartNfa_0(0, old0, old1); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0, 0L);
      return 2;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x4402000L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x200000L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x9080000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x800900000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x9000L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x40000L);
      case 116:
         if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(2, 14, 13);
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x200000000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0, 0L);
}
private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0, 0L);
      return 3;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0xa00000L);
      case 100:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(3, 24, 13);
         else if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(3, 26, 13);
         break;
      case 101:
         if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(3, 18, 13);
         else if ((active0 & 0x200000000L) != 0L)
            return jjStartNfaWithStates_0(3, 33, 13);
         break;
      case 103:
         return jjMoveStringLiteralDfa4_0(active0, 0x800001000L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000L);
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x88000L);
      case 110:
         return jjMoveStringLiteralDfa4_0(active0, 0x8000000L);
      case 115:
         return jjMoveStringLiteralDfa4_0(active0, 0x400002000L);
      case 116:
         return jjMoveStringLiteralDfa4_0(active0, 0x500000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0, 0L);
}
private final int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0, 0L);
      return 4;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(4, 19, 13);
         else if ((active0 & 0x400000000L) != 0L)
            return jjStartNfaWithStates_0(4, 34, 13);
         return jjMoveStringLiteralDfa5_0(active0, 0x8000L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x500000L);
      case 107:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(4, 21, 13);
         break;
      case 108:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(4, 23, 13);
         break;
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x10000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x2001000L);
      case 115:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(4, 13, 13);
         break;
      case 116:
         if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(4, 27, 13);
         return jjMoveStringLiteralDfa5_0(active0, 0x800000000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0, 0L);
}
private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0, 0L);
      return 5;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x9000L);
      case 99:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(5, 22, 13);
         break;
      case 103:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(5, 16, 13);
         break;
      case 104:
         if ((active0 & 0x800000000L) != 0L)
            return jjStartNfaWithStates_0(5, 35, 13);
         break;
      case 110:
         if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(5, 25, 13);
         return jjMoveStringLiteralDfa6_0(active0, 0x100000L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0, 0L);
}
private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0, 0L);
      return 6;
   }
   switch(curChar)
   {
      case 109:
         if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(6, 12, 13);
         break;
      case 110:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(6, 15, 13);
         break;
      case 117:
         return jjMoveStringLiteralDfa7_0(active0, 0x100000L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0, 0L);
}
private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0, 0L);
      return 7;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(7, 20, 13);
         break;
      default :
         break;
   }
   return jjStartNfa_0(6, active0, 0L);
}
private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
static final long[] jjbitVec0 = {
   0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec2 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 27;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 28)
                        kind = 28;
                     jjCheckNAdd(1);
                  }
                  else if (curChar == 47)
                     jjAddStates(0, 1);
                  else if (curChar == 48)
                  {
                     if (kind > 28)
                        kind = 28;
                     jjCheckNAddTwoStates(15, 17);
                  }
                  else if (curChar == 34)
                     jjCheckNAddStates(2, 4);
                  break;
               case 21:
                  if (curChar == 47)
                  {
                     if (kind > 8)
                        kind = 8;
                     jjCheckNAddStates(5, 7);
                  }
                  else if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjCheckNAdd(1);
                  break;
               case 2:
                  if (curChar == 34)
                     jjCheckNAddStates(2, 4);
                  break;
               case 3:
                  if ((0xfffffffbffffdbffL & l) != 0L)
                     jjCheckNAddStates(2, 4);
                  break;
               case 5:
                  if ((0x8400000000L & l) != 0L)
                     jjCheckNAddStates(2, 4);
                  break;
               case 6:
                  if (curChar == 34 && kind > 32)
                     kind = 32;
                  break;
               case 7:
                  if ((0xff000000000000L & l) != 0L)
                     jjCheckNAddStates(8, 11);
                  break;
               case 8:
                  if ((0xff000000000000L & l) != 0L)
                     jjCheckNAddStates(2, 4);
                  break;
               case 9:
                  if ((0xf000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 10:
                  if ((0xff000000000000L & l) != 0L)
                     jjCheckNAdd(8);
                  break;
               case 13:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 36)
                     kind = 36;
                  jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 14:
                  if (curChar != 48)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjCheckNAddTwoStates(15, 17);
                  break;
               case 16:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 17:
                  if ((0xff000000000000L & l) == 0L)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjCheckNAdd(17);
                  break;
               case 18:
                  if (curChar == 47)
                     jjAddStates(0, 1);
                  break;
               case 19:
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 20;
                  break;
               case 20:
                  if ((0xffff7fffffffffffL & l) != 0L && kind > 6)
                     kind = 6;
                  break;
               case 22:
                  if (curChar != 47)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjCheckNAddStates(5, 7);
                  break;
               case 23:
                  if ((0xffffffffffffdbffL & l) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjCheckNAddStates(5, 7);
                  break;
               case 24:
                  if ((0x2400L & l) != 0L && kind > 8)
                     kind = 8;
                  break;
               case 25:
                  if (curChar == 10 && kind > 8)
                     kind = 8;
                  break;
               case 26:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 36)
                        kind = 36;
                     jjCheckNAdd(13);
                  }
                  else if (curChar == 95)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 3:
                  if ((0xffffffffefffffffL & l) != 0L)
                     jjCheckNAddStates(2, 4);
                  break;
               case 4:
                  if (curChar == 92)
                     jjAddStates(12, 14);
                  break;
               case 5:
                  if ((0x14404410000000L & l) != 0L)
                     jjCheckNAddStates(2, 4);
                  break;
               case 11:
                  if (curChar == 95)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 12:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 36)
                     kind = 36;
                  jjCheckNAdd(13);
                  break;
               case 13:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 36)
                     kind = 36;
                  jjCheckNAdd(13);
                  break;
               case 15:
                  if ((0x100000001000000L & l) != 0L)
                     jjCheckNAdd(16);
                  break;
               case 16:
                  if ((0x7e0000007eL & l) == 0L)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjCheckNAdd(16);
                  break;
               case 20:
                  if (kind > 6)
                     kind = 6;
                  break;
               case 23:
                  if (kind > 8)
                     kind = 8;
                  jjAddStates(5, 7);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 3:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     jjAddStates(2, 4);
                  break;
               case 20:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 6)
                     kind = 6;
                  break;
               case 23:
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjAddStates(5, 7);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 27 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjMoveStringLiteralDfa0_2()
{
   switch(curChar)
   {
      case 42:
         return jjMoveStringLiteralDfa1_2(0x400L);
      default :
         return 1;
   }
}
private final int jjMoveStringLiteralDfa1_2(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 1;
   }
   switch(curChar)
   {
      case 47:
         if ((active0 & 0x400L) != 0L)
            return jjStopAtPos(1, 10);
         break;
      default :
         return 2;
   }
   return 2;
}
private final int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 42:
         return jjMoveStringLiteralDfa1_1(0x200L);
      default :
         return 1;
   }
}
private final int jjMoveStringLiteralDfa1_1(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 1;
   }
   switch(curChar)
   {
      case 47:
         if ((active0 & 0x200L) != 0L)
            return jjStopAtPos(1, 9);
         break;
      default :
         return 2;
   }
   return 2;
}
static final int[] jjnextStates = {
   21, 22, 3, 4, 6, 23, 24, 26, 3, 4, 8, 6, 5, 7, 9, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec2[i2] & l2) != 0L);
      default : 
         if ((jjbitVec0[i1] & l1) != 0L)
            return true;
         return false;
   }
}
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, 
"\120\162\157\147\162\141\155", "\143\154\141\163\163", "\151\156\164", "\142\157\157\154\145\141\156", 
"\123\164\162\151\156\147", "\151\146", "\145\154\163\145", "\167\150\151\154\145", 
"\143\157\156\164\151\156\165\145", "\142\162\145\141\153", "\163\164\141\164\151\143", "\146\151\156\141\154", 
"\166\157\151\144", "\162\145\164\165\162\156", "\162\145\141\144", "\160\162\151\156\164", null, 
null, null, null, null, "\164\162\165\145", "\146\141\154\163\145", 
"\154\145\156\147\164\150", null, null, null, "\50", "\51", "\173", "\175", "\133", "\135", "\73", "\54", 
"\56", "\75", "\74", "\76", "\41", "\75\75", "\74\75", "\76\75", "\41\75", 
"\174\174", "\46\46", "\53\53", "\55\55", "\53", "\55", "\52", "\57", "\45", "\53\75", 
"\55\75", "\52\75", "\57\75", "\45\75", };
public static final String[] lexStateNames = {
   "DEFAULT", 
   "IN_FORMAL_COMMENT", 
   "IN_MULTI_LINE_COMMENT", 
};
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, 1, 2, -1, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0xffffff9f1ffff001L, 0x3fL, 
};
static final long[] jjtoSkip = {
   0x73eL, 0x0L, 
};
static final long[] jjtoSpecial = {
   0x700L, 0x0L, 
};
static final long[] jjtoMore = {
   0x8c0L, 0x0L, 
};
protected JavaCharStream input_stream;
private final int[] jjrounds = new int[27];
private final int[] jjstateSet = new int[54];
StringBuffer image;
int jjimageLen;
int lengthOfMatch;
protected char curChar;
public ParserTokenManager(JavaCharStream stream){
   if (JavaCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}
public ParserTokenManager(JavaCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}
public void ReInit(JavaCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 27; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
public void ReInit(JavaCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
public void SwitchTo(int lexState)
{
   if (lexState >= 3 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

public Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }
   image = null;
   jjimageLen = 0;

   for (;;)
   {
     switch(curLexState)
     {
       case 0:
         try { input_stream.backup(0);
            while (curChar <= 32 && (0x100003600L & (1L << curChar)) != 0L)
               curChar = input_stream.BeginToken();
         }
         catch (java.io.IOException e1) { continue EOFLoop; }
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         break;
       case 1:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_1();
         if (jjmatchedPos == 0 && jjmatchedKind > 11)
         {
            jjmatchedKind = 11;
         }
         break;
       case 2:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_2();
         if (jjmatchedPos == 0 && jjmatchedKind > 11)
         {
            jjmatchedKind = 11;
         }
         break;
     }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           matchedToken.specialToken = specialToken;
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
           {
              matchedToken = jjFillToken();
              if (specialToken == null)
                 specialToken = matchedToken;
              else
              {
                 matchedToken.specialToken = specialToken;
                 specialToken = (specialToken.next = matchedToken);
              }
              SkipLexicalActions(matchedToken);
           }
           else 
              SkipLexicalActions(null);
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
        MoreLexicalActions();
      if (jjnewLexState[jjmatchedKind] != -1)
        curLexState = jjnewLexState[jjmatchedKind];
        curPos = 0;
        jjmatchedKind = 0x7fffffff;
        try {
           curChar = input_stream.readChar();
           continue;
        }
        catch (java.io.IOException e1) { }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
  }
}

void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void MoreLexicalActions()
{
   jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
   switch(jjmatchedKind)
   {
      case 6 :
         if (image == null)
            image = new StringBuffer();
         image.append(input_stream.GetSuffix(jjimageLen));
         jjimageLen = 0;
                   input_stream.backup(1);
         break;
      default : 
         break;
   }
}
}
