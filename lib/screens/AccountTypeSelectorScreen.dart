import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:med_channel/screens/physician/RegistrationScreen.dart';

class AccountTypeSelectorScreen extends StatefulWidget {
  const AccountTypeSelectorScreen({super.key});

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
                  MaterialPageRoute(builder: (context) => const RegisterationScreen()),
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
