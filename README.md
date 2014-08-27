adrawerlayoutlib
================

See copyright notice and license in ADrawerLayout.java

ZDrawerLayout by Edward McGrath - edward@edwardmcgrath.com

A drawer menu widget for Android where the menu items appear behind the content.  The menu items grow as the drawer is scrolled open.

ADrawerLayout - A Generic Drawer Layout (with minor changes to allow for extending)

ZDrawerLayout - Extends ADrawerLayout.  ZDrawerLayout acomplished a specific menu effect where the menu items sit behind the content and grow forward as the drawer is scrolled open.

The layout which contains a ZDrawerLayout should look something like this (pseudo, see activity_main.xml for an actual layout used in sample activity MainActivity):

<ZDrawerLayout>

  <LinearLayout/>       <---- layout containing menu items which appear behind the content until drawer opens

  <ScrollView/>         <---- content sits inside the ScrollView
  
</ZDrawerLayout>


MainActivity - Sample activity that uses ZDrawerLayout.  A minimalist activity which contains a listview holding the menu items and a layout with a single textview (activity_main.xml) as the content.
