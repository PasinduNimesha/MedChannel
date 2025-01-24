import 'package:flutter/material.dart';

class SettingsTab extends StatelessWidget {
  const SettingsTab({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Settings"),
        backgroundColor: Colors.teal,
      ),
      body: ListView(
        padding: const EdgeInsets.all(10),
        children: [
          const ListTile(
            title: Text("Account"),
            leading: Icon(Icons.person),
          ),
          const Divider(),
          ListTile(
            title: const Text("Privacy"),
            leading: const Icon(Icons.lock),
            onTap: () {
              // Navigate to privacy settings or handle logic
            },
          ),
          ListTile(
            title: const Text("Notifications"),
            leading: const Icon(Icons.notifications),
            onTap: () {
              // Navigate to notification settings
            },
          ),
          const Divider(),
          SwitchListTile(
            title: const Text("Dark Mode"),
            secondary: const Icon(Icons.dark_mode),
            value: false,
            onChanged: (value) {
              // Handle dark mode toggle
            },
          ),
          ListTile(
            title: const Text("Language"),
            leading: const Icon(Icons.language),
            onTap: () {
              // Navigate to language settings
            },
          ),
          const Divider(),
          ListTile(
            title: const Text("About"),
            leading: const Icon(Icons.info),
            onTap: () {
              // Navigate to about page or show info dialog
            },
          ),
          const Divider(),
          ListTile(
            title: const Text("Log Out"),
            leading: const Icon(Icons.logout),
            onTap: () {
              // Handle log out
            },
          ),
        ],
      ),
    );
  }
}
