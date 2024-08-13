import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:med_channel/screens/physician/RegistrationScreen.dart';

class AccountTypeSelectorScreen extends StatefulWidget {
  final String id;
  const AccountTypeSelectorScreen({super.key, required this.id});

  @override
  State<AccountTypeSelectorScreen> createState() => _AccountTypeSelectorScreenState();
}

class _AccountTypeSelectorScreenState extends State<AccountTypeSelectorScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Account Type Selector'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: null,
              child: const Text('Patient'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => RegisterationScreen(id: widget.id)),
                );
              },
              child: const Text('Physician'),
            ),
          ],
        ),
      ),
    );
  }
}