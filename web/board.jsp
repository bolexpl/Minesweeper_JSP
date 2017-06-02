<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-xs-5 text-right"><span class="licznik">Pozostałe miny: <span id="countMines">99</span></span></div>
    <div class="col-xs-2">
        <button class="center-block btn btn-default">
            <img src="res/smiley1.ico" alt="" class="new-game" id="new-game">
        </button>
    </div>
    <div class="col-xs-5"><span class="licznik">Czas: <span id="timer">0</span>s</span></div>
</div>

<div class="row">
    <div class="col-xs-12 board-container">
        <table id="table">
            <tbody>
            </tbody>
        </table>
    </div>
</div>
<!--
<div class="row">
<div class="col-xs-12">
<table>
<tbody>
<tr>
<td class="blank field1">
<div></div>
</td>
<td class="blank field2">
<div></div>
</td>
<td class="blank field3">
<div></div>
</td>
<td class="blank field4">
<div></div>
</td>
<td class="blank field5">
<div></div>
</td>
<td class="blank field6">
<div></div>
</td>
<td class="blank field7">
<div></div>
</td>
<td class="blank field8">
<div></div>
</td>
<td class="blank">
<div></div>
</td>
<td class="blank mine-red">
<div></div>
</td>
<td class="flag">
<div></div>
</td>
<td class="mine">
<div></div>
</td>
<td>
<div></div>
</td>
</tr>
</tbody>
</table>
</div>
</div>
-->
<div class="row">
    <div class="col-xs-12">
        <button class="btn btn-default center-block" onclick="back()">
            Wróć
        </button>
    </div>
</div>