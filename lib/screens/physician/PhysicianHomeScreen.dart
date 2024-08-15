import 'package:flutter/material.dart';
import 'package:med_channel/styles/colors.dart';
import 'package:med_channel/tabs/ChatTab.dart';
import 'package:med_channel/tabs/patient/PatientHomeTab.dart';
import 'package:med_channel/tabs/ScheduleTab.dart';
import 'package:med_channel/tabs/SettingsTab.dart';
import 'package:med_channel/tabs/physician/PhysicianHomeTab.dart';

class PhysicianHomeScreen extends StatefulWidget {
  const PhysicianHomeScreen({Key? key}) : super(key: key);

  @override
  _PhysicianHomeScreenState createState() => _PhysicianHomeScreenState();
}

List<Map> navigationBarItems = [
  {'icon': Icons.local_hospital, 'index': 0},
  {'icon': Icons.calendar_today, 'index': 1},
  {'icon': Icons.chat, 'index': 2},
  {'icon': Icons.settings, 'index': 3}
];

class _PhysicianHomeScreenState extends State<PhysicianHomeScreen> {
  int _selectedIndex = 0;
  void goToSchedule() {
    setState(() {
      _selectedIndex = 1;
    });
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> screens = [
      Physicianhometab(),
      ScheduleTab(),
      ChatTab(),
      SettingsTab()
    ];

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color(MyColors.primary),
        elevation: 0,
        toolbarHeight: 0,
      ),
      body: SafeArea(
        child: screens[_selectedIndex],
      ),
      bottomNavigationBar: BottomNavigationBar(
        selectedFontSize: 0,
        selectedItemColor: Color(MyColors.primary),
        showSelectedLabels: false,
        showUnselectedLabels: false,
        items: [
          for (var navigationBarItem in navigationBarItems)
            BottomNavigationBarItem(
              icon: Container(
                height: 55,
                decoration: BoxDecoration(
                  border: Border(
                    top: _selectedIndex == navigationBarItem['index']
                        ? BorderSide(color: Color(MyColors.bg01), width: 5)
                        : BorderSide.none,
                  ),
                ),
                child: Icon(
                  navigationBarItem['icon'],
                  color: _selectedIndex == 0
                      ? Color(MyColors.bg01)
                      : Color(MyColors.bg02),
                ),
              ),
              label: '',
            ),
        ],
        currentIndex: _selectedIndex,
        onTap: (value) => setState(() {
          _selectedIndex = value;
        }),
      ),
    );
  }
}