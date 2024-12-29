import 'package:flutter/material.dart';

class ChatLogsTab extends StatefulWidget {
  const ChatLogsTab({super.key});

  @override
  State<ChatLogsTab> createState() => _ChatTabState();
}

class _ChatTabState extends State<ChatLogsTab> {
  final List<Map<String, dynamic>> chatList = [
    {
      "name": "John Doe",
      "message": "Hey! How are you?",
      "time": "10:30 AM",
      "avatarUrl": "https://randomuser.me/api/portraits/men/1.jpg",
    },
    {
      "name": "Jane Smith",
      "message": "Let’s catch up later!",
      "time": "9:45 AM",
      "avatarUrl": "https://randomuser.me/api/portraits/women/2.jpg",
    },
    {
      "name": "David Johnson",
      "message": "Check out the photos I sent.",
      "time": "Yesterday",
      "avatarUrl": "https://randomuser.me/api/portraits/men/3.jpg",
    },
    {
      "name": "Emily Davis",
      "message": "Can we reschedule our meeting?",
      "time": "Monday",
      "avatarUrl": "https://randomuser.me/api/portraits/women/4.jpg",
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Chats'),
        actions: [
          IconButton(
            icon: const Icon(Icons.search),
            onPressed: () {
              // Add search functionality
            },
          ),
          PopupMenuButton(
            itemBuilder: (context) => [
              const PopupMenuItem(value: 'settings', child: Text('Settings')),
              const PopupMenuItem(value: 'logout', child: Text('Logout')),
            ],
            onSelected: (value) {
              if (value == 'settings') {
                // Navigate to settings
              } else if (value == 'logout') {
                // Perform logout
              }
            },
          ),
        ],
      ),
      body: ListView.builder(
        itemCount: chatList.length,
        itemBuilder: (context, index) {
          final chat = chatList[index];
          return ListTile(
            leading: CircleAvatar(
              backgroundImage: NetworkImage(chat['avatarUrl']),
            ),
            title: Text(chat['name']),
            subtitle: Text(chat['message']),
            trailing: Text(
              chat['time'],
              style: TextStyle(color: Colors.grey, fontSize: 12),
            ),
            onTap: () {
              // Navigate to chat screen
              print('Tapped on ${chat['name']}');
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          // Add new chat functionality
        },
        child: const Icon(Icons.chat),
      ),
    );
  }
}
