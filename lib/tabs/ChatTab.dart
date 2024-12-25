import 'package:flutter/material.dart';
import 'package:med_channel/styles/colors.dart';

class ChatTab extends StatefulWidget {
  const ChatTab({super.key});

  @override
  State<ChatTab> createState() => _ChatTabState();
}

class _ChatTabState extends State<ChatTab> {
  final List<Map<String, String>> _messages = [];
  final TextEditingController _messageController = TextEditingController();

  void _sendMessage() {
    final text = _messageController.text.trim();
    if (text.isNotEmpty) {
      setState(() {
        _messages.add({"text": text, "sender": "user"});
      });
      _messageController.clear();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Chat"),
        backgroundColor: Color(MyColors.primary),
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: _messages.length,
              reverse: true,
              padding: const EdgeInsets.all(10),
              itemBuilder: (context, index) {
                final message = _messages[_messages.length - index - 1];
                final isUserMessage = message["sender"] == "user";

                return Align(
                  alignment:
                  isUserMessage ? Alignment.centerRight : Alignment.centerLeft,
                  child: Container(
                    margin: const EdgeInsets.symmetric(vertical: 5),
                    padding: const EdgeInsets.all(10),
                    decoration: BoxDecoration(
                      color: isUserMessage ? Color(MyColors.primary) : Colors.grey[300],
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Text(
                      message["text"]!,
                      style: TextStyle(
                        color: isUserMessage ? Colors.white : Colors.black,
                      ),
                    ),
                  ),
                );
              },
            ),
          ),
          const Divider(height: 1),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: _messageController,
                    decoration: InputDecoration(
                      hintText: "Type a message",
                      filled: true,
                      fillColor: Colors.grey[200],
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(25),
                        borderSide: BorderSide.none,
                      ),
                    ),
                  ),
                ),
                const SizedBox(width: 5),
                IconButton(
                  icon: const Icon(Icons.send),
                  color: Color(MyColors.primary),
                  onPressed: _sendMessage,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
